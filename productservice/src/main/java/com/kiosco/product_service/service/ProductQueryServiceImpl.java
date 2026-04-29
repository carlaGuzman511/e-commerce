package com.kiosco.product_service.service;

import com.kiosco.product_service.client.MediaClient;
import com.kiosco.product_service.context.TenantContext;
import com.kiosco.product_service.domain.Product;
import com.kiosco.product_service.dto.ProductSpecs;
import com.kiosco.product_service.dto.mapper.MediaMapperUtil;
import com.kiosco.product_service.dto.mapper.ProductMapper;
import com.kiosco.product_service.dto.response.ProductResponse;
import com.kiosco.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductQueryServiceImpl implements ProductQueryService {

    private final MediaClient mediaClient;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public Page<ProductResponse> getAll(Pageable pageable,
                                        Long categoryId,
                                        String sort,
                                        String filter,
                                        Boolean active) {
        String tenantId = TenantContext.get();

        Specification<Product> spec = Specification.allOf(ProductSpecs.tenantIs(tenantId));

        if (categoryId != null)
            spec = spec.and(ProductSpecs.categoryIs(categoryId));

        if (Boolean.TRUE.equals(active))
            spec = spec.and(ProductSpecs.isActive());

        if ("lowStock".equalsIgnoreCase(filter))
            spec = spec.and(ProductSpecs.lowStock());

        if ("expiring".equalsIgnoreCase(filter))
            spec = spec.and(ProductSpecs.expiringSoon(LocalDate.now().plusDays(30)));

        if ("new".equalsIgnoreCase(filter))
            spec = spec.and(ProductSpecs.createdRecently(LocalDate.now().minusDays(15)));

        Pageable sortedPageable = applySort(pageable, sort);

        return productRepository.findAll(spec, sortedPageable)
                .map(product -> {
                    ProductResponse response = productMapper.toResponse(product);
                    MediaMapperUtil.mapMedia(response, product, mediaClient);
                    return response;
                });
    }

    @Override
    public Page<ProductResponse> getExpiringSoon(Pageable pageable) {
        String tenantId = TenantContext.get();
        var spec = Specification.allOf(ProductSpecs.tenantIs(tenantId))
                .and(ProductSpecs.expiringSoon(LocalDate.now().plusDays(30)));
        return productRepository.findAll(spec, pageable).map(productMapper::toResponse);
    }

    @Override
    public Page<ProductResponse> getLowStock(Pageable pageable) {
        String tenantId = TenantContext.get();
        var spec = Specification.allOf(ProductSpecs.tenantIs(tenantId))
                .and(ProductSpecs.lowStock());
        return productRepository.findAll(spec, pageable).map(productMapper::toResponse);
    }

    private Pageable applySort(Pageable pageable, String sort) {
        if (sort == null) return pageable;

        return switch (sort.toLowerCase()) {
            case "alphabetical" ->
                    PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("name").ascending());
            case "latest" ->
                    PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("createdAt").descending());
            case "priceasc" ->
                    PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("price").ascending());
            case "pricedesc" ->
                    PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("price").descending());
            default -> pageable;
        };
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> findAllWithStockByTenant(String tenantId, Pageable pageable) {
        Page<Product> products = productRepository.findAllWithStockByTenant(tenantId, pageable);

        return products.map(product -> {
            ProductResponse response = productMapper.toResponse(product);
            MediaMapperUtil.mapMedia(response, product, mediaClient);
            return response;
        });
    }


    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> searchProducts(
            String tenantId, Pageable pageable, String name,
            Long categoryId, Double minPrice, Double maxPrice) {

        Page<Product> products = productRepository.searchProducts(
                tenantId, name, categoryId, minPrice, maxPrice, pageable
        );

        return products.map(productMapper::toResponse);
    }
}
