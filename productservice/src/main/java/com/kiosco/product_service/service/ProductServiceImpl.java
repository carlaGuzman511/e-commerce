package com.kiosco.product_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kiosco.product_service.client.MediaClient;
import com.kiosco.product_service.constants.Messages;
import com.kiosco.product_service.domain.*;
import com.kiosco.product_service.dto.mapper.ProductMapper;
import com.kiosco.product_service.dto.request.*;
import com.kiosco.product_service.dto.response.MediaResponse;
import com.kiosco.product_service.dto.response.ProductResponse;
import com.kiosco.product_service.dto.response.ProductVariantResponse;
import com.kiosco.product_service.dto.response.StockResponse;
import com.kiosco.product_service.enums.MovementReason;
import com.kiosco.product_service.enums.ProductStatus;
import com.kiosco.product_service.exeption.BusinessException;
import com.kiosco.product_service.exeption.NotFoundException;
import com.kiosco.product_service.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BranchRepository branchRepository;
    private final ProductVariantRepository productVariantRepository;
    private final ProductBranchRepository productBranchRepository;
    private final InventoryMovementRepository inventoryMovementRepository;
    private final ProductMapper productMapper;
    private final ObjectMapper objectMapper;
    private final MediaClient mediaClient;

    @Override
    public boolean existsByNameAndTenant(String tenantId, String name) {
        log.debug(Messages.LOG_PRODUCT_CHECK, tenantId, name);
        return productRepository.existsByTenantIdAndNameIgnoreCase(tenantId, name);
    }

    @Transactional
    @Override
    public ProductResponse create(String tenantId, ProductCreateRequest request) {
        log.info(Messages.CREATING_PRODUCT_LOG, tenantId, request.getName());

        Product product = createProductByRequest(tenantId, request);
        productRepository.save(product);

        List<ProductVariant> variants = new ArrayList<>();
        for (ProductVariantCreateRequest vReq : request.getVariants()) {
            ProductVariant variant = createProductVariant(vReq, product, tenantId);
            variants.add(variant);
        }

        product.setVariants(variants);
        productRepository.save(product);

        ProductResponse response = productMapper.toResponse(product);
        mapMediaResponseToProductResponse(response, product);

        log.info("[{}] {}", tenantId, String.format(Messages.PRODUCT_CREATED_SUCCESS, product.getId()));
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getById(String tenantId, Long productId) {
        log.info("[{}] {}", tenantId, String.format(Messages.FETCHING_PRODUCT_BY_ID, productId));

        Product product = productRepository.findByIdAndTenantIdAndActiveTrue(productId, tenantId)
                .orElseThrow(() -> new NotFoundException(Messages.PRODUCT_NOT_FOUND));

        ProductResponse response = productMapper.toResponse(product);
        mapMediaResponseToProductResponse(response, product);

        log.info("[{}] {}", tenantId, String.format(Messages.PRODUCT_FETCH_SUCCESS, productId));
        return response;
    }

    @Transactional
    public void reserveStock(String tenantId, List<StockUpdateRequest> items) {
        log.info("[{}] {}", tenantId, Messages.RESERVING_STOCK);
        for (StockUpdateRequest item : items) {
            Long branchId = item.getBranchId();
            Long variantId = item.getVariantId();
            Integer quantity = item.getQuantity();

            if (branchId == null || variantId == null || quantity == null || quantity <= 0) {
                throw new BusinessException(Messages.STOCK_INVALID);
            }

            ProductBranch productBranch = productBranchRepository
                    .findByBranch_IdAndProductVariant_Id(branchId, variantId)
                    .orElseThrow(() -> new NotFoundException(Messages.STOCK_NOT_FOUND));

            if (!productBranch.getBranch().getTenantId().equals(tenantId)) {
                throw new BusinessException(Messages.BRANCH_TENANT_MISMATCH);
            }

            if (productBranch.getStock() < quantity) {
                throw new BusinessException(Messages.STOCK_INSUFFICIENT);
            }

            productBranch.setStock(productBranch.getStock() - quantity);
            productBranchRepository.save(productBranch);

            InventoryMovement movement = InventoryMovement.builder()
                    .branch(productBranch.getBranch())
                    .variant(productBranch.getProductVariant())
                    .quantityChange(-quantity)
                    .reason(MovementReason.ORDER_RESERVATION)
                    .build();

            inventoryMovementRepository.save(movement);
            log.debug("[{}] {}", tenantId, String.format(Messages.STOCK_RESERVED, variantId));
        }
    }

    @Override
    @Transactional
    public void restoreStock(String tenantId, List<StockUpdateRequest> items) {
        log.info("[{}] {}", tenantId, Messages.RESTORING_STOCK);
        for (StockUpdateRequest item : items) {
            ProductBranch productBranch = productBranchRepository
                    .findByBranch_IdAndProductVariant_Id(item.getBranchId(), item.getVariantId())
                    .orElseThrow(() -> new NotFoundException(Messages.STOCK_NOT_FOUND));

            productBranch.setStock(productBranch.getStock() + item.getQuantity());
            productBranchRepository.save(productBranch);

            InventoryMovement movement = InventoryMovement.builder()
                    .branch(productBranch.getBranch())
                    .variant(productBranch.getProductVariant())
                    .quantityChange(item.getQuantity())
                    .reason(MovementReason.ORDER_RESTORE)
                    .build();

            inventoryMovementRepository.save(movement);
            log.debug("[{}] {}", tenantId, String.format(Messages.STOCK_RESTORED, item.getVariantId()));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public StockResponse getStock(String tenantId, StockCheckRequest request) {
        log.info(Messages.LOG_CHECKING_STOCK, tenantId, request.getVariantId(), request.getBranchId());

        if (request.getBranchId() == null || request.getVariantId() == null) {
            throw new BusinessException(Messages.STOCK_INVALID);
        }

        var productBranch = productBranchRepository
                .findByBranch_IdAndProductVariant_Id(request.getBranchId(), request.getVariantId())
                .orElseThrow(() -> new NotFoundException(
                        String.format(Messages.STOCK_NOT_FOUND_WITH_IDS, request.getVariantId(), request.getBranchId())));

        if (!productBranch.getBranch().getTenantId().equals(tenantId)) {
            throw new BusinessException(String.format(Messages.BRANCH_TENANT_MISMATCH, tenantId));
        }

        long currentStock = productBranch.getStock();
        long requested = request.getRequested() != null ? request.getRequested() : 0L;

        StockResponse response = new StockResponse();
        response.setAvailable(currentStock >= requested);
        response.setStock(currentStock);
        response.setRequested(requested);
        response.setBranchId(productBranch.getBranch().getId().intValue());
        response.setProductId(productBranch.getProductVariant().getProduct().getId());
        response.setVariantId(productBranch.getProductVariant().getId());

        log.debug(Messages.LOG_STOCK_STATUS, tenantId, currentStock, requested, response.getAvailable());
        return response;
    }

    @Transactional
    @Override
    public boolean delete(Long id, String tenantId) {
        log.info("[{}] {}", tenantId, String.format(Messages.DELETING_PRODUCT, id));

        Product product = productRepository.findByIdAndTenantId(id, tenantId)
                .orElseThrow(() -> new NotFoundException(String.format(Messages.PRODUCT_NOT_FOUND_WITH_ID, id)));

        product.setActive(false);
        productRepository.save(product);

        log.info("[{}] {}", tenantId, String.format(Messages.PRODUCT_DELETED_SUCCESS, id));
        return true;
    }

    private Product createProductByRequest(String tenantId, ProductCreateRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new NotFoundException(Messages.CATEGORY_NOT_FOUND));

        Product product = new Product();
        BeanUtils.copyProperties(request, product,
                "id", "tenantId", "status", "category", "createdAt", "updatedAt");
        product.setTenantId(tenantId);
        product.setCategory(category);
        product.setStatus(ProductStatus.ACTIVE);

        if (Objects.isNull(product.getMinStock())) {
            product.setMinStock(0);
        }
        return product;
    }

    private ProductVariant createProductVariant(ProductVariantCreateRequest req, Product product, String tenantId) {
        ProductVariant variant = new ProductVariant();
        BeanUtils.copyProperties(req, variant, "id", "product", "createdAt", "updatedAt");
        variant.setProduct(product);
        variant.setCreatedAt(Instant.now());
        variant.setUpdatedAt(Instant.now());

        try {
            String jsonAttributes = req.getAttributes() != null
                    ? objectMapper.writeValueAsString(req.getAttributes())
                    : "{}";
            variant.setAttributes(jsonAttributes);
        } catch (Exception e) {
            throw new BusinessException(String.format(Messages.INVALID_ATTRIBUTES_JSON, req.getProductCode()));
        }

        if (req.getImages() != null && !req.getImages().isEmpty()) {
            variant.setImages(req.getImages());
        }

        variant = productVariantRepository.save(variant);

        List<ProductBranch> pbList = new ArrayList<>();
        for (BranchStockRequest bs : req.getBranchStocks()) {
            ProductBranch productBranch = findByIdAndTenantId(tenantId, bs, variant);
            pbList.add(productBranch);
        }
        productBranchRepository.saveAll(pbList);
        variant.setProductBranches(pbList);
        return variant;
    }

    private ProductBranch findByIdAndTenantId(String tenantId, BranchStockRequest branchStockRequest, ProductVariant variant) {
        Branch branch = branchRepository.findByIdAndTenantId(branchStockRequest.getBranchId(), tenantId)
                .orElseThrow(() ->
                        new NotFoundException(String.format(Messages.BRANCH_NOT_FOUND_WITH_ID, branchStockRequest.getBranchId())));

        ProductBranch productBranch = new ProductBranch();
        productBranch.setBranch(branch);
        productBranch.setProductVariant(variant);
        productBranch.setStock(branchStockRequest.getStock());
        return productBranch;
    }

    private List<MediaResponse> getImagesByMediaClient(List<String> images) {
        if (images == null || images.isEmpty()) {
            return List.of();
        }
        try {
            PublicIdsRequest publicIdsRequest = new PublicIdsRequest(images);
            return mediaClient.getByPublicIds(publicIdsRequest).getData();
        } catch (Exception e) {
            log.error(Messages.LOG_MEDIA_SERVICE_ERROR, e.getMessage());
            return List.of();
        }
    }

    private void mapMediaResponseToProductResponse(ProductResponse productResponse, Product product) {
        for (ProductVariantResponse variantResponse : productResponse.getVariants()) {
            ProductVariant variantEntity = product.getVariants().stream()
                    .filter(v -> v.getId().equals(variantResponse.getId()))
                    .findFirst()
                    .orElse(null);

            if (variantEntity == null || CollectionUtils.isEmpty(variantEntity.getImages())) {
                continue;
            }

            try {
                List<MediaResponse> mediaResponses = getImagesByMediaClient(variantEntity.getImages());
                variantResponse.setImages(mediaResponses);
            } catch (Exception e) {
                log.warn(Messages.LOG_MEDIA_FETCH_WARNING, variantResponse.getProductCode(), e.getMessage());
                variantResponse.setImages(List.of());
            }
        }
    }

    @Override
    @Transactional
    public ProductResponse update(String tenantId, ProductUpdateRequest request) {

        Product product = new Product();
        productRepository.save(product);

        productRepository.save(product);
        return productMapper.toResponse(product);
    }
}
