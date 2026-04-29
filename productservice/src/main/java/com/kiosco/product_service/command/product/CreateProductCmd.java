package com.kiosco.product_service.command.product;

import com.kiosco.product_service.context.TenantContext;
import com.kiosco.product_service.command.spec.SafeAbstractCommand;
import com.kiosco.product_service.constants.Messages;
import com.kiosco.product_service.dto.request.BranchStockRequest;
import com.kiosco.product_service.dto.request.ProductCreateRequest;
import com.kiosco.product_service.dto.request.ProductVariantCreateRequest;
import com.kiosco.product_service.dto.response.ProductResponse;
import com.kiosco.product_service.exeption.BusinessException;
import com.kiosco.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateProductCmd extends SafeAbstractCommand<ProductCreateRequest, ProductResponse> {
    private final ProductService productService;

    @Override
    protected void preExecute(ProductCreateRequest request) {
        String tenantId = TenantContext.get();
        if (request.getVariants() == null || request.getVariants().isEmpty()) {
            throw new BusinessException(Messages.PRODUCT_VARIANT_REQUIRED);
        }

        for (ProductVariantCreateRequest variantCreateRequest : request.getVariants()) {
            if (variantCreateRequest.getPrice() == null || variantCreateRequest.getPrice().doubleValue() <= 0) {
                throw new BusinessException(Messages.PRODUCT_PRICE_INVALID);
            }
            if (variantCreateRequest.getBranchStocks() == null || variantCreateRequest.getBranchStocks().isEmpty()) {
                throw new BusinessException(Messages.PRODUCT_VARIANT_STOCK_REQUIRED);
            }
            for (BranchStockRequest stockRequest : variantCreateRequest.getBranchStocks()) {
                if (stockRequest.getStock() == null || stockRequest.getStock() < 0) {
                    throw new BusinessException(Messages.PRODUCT_STOCK_NEGATIVE);
                }
            }
        }

        if (productService.existsByNameAndTenant(tenantId, request.getName())) {
            throw new BusinessException(Messages.PRODUCT_NAME_DUPLICATE);
        }
    }

    @Override
    protected ProductResponse safeExecute(ProductCreateRequest request) {
        String tenantId = TenantContext.get();
        return productService.create(tenantId, request);
    }

    @Override
    protected void postExecute(ProductResponse response) {
        log.info("Product created with ID: {}", response.getId());
    }
}
