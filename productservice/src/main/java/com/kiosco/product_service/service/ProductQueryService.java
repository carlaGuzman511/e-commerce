package com.kiosco.product_service.service;

import com.kiosco.product_service.dto.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductQueryService {
    Page<ProductResponse> getAll(
            Pageable pageable,
            Long categoryId,
            String sort,
            String filter,
            Boolean active
    );

    Page<ProductResponse> searchProducts(
            String tenantId,
            Pageable pageable,
            String name,
            Long categoryId,
            Double minPrice,
            Double maxPrice);

    Page<ProductResponse> findAllWithStockByTenant(String tenantId, Pageable pageable);

    Page<ProductResponse> getLowStock(Pageable pageable);
    Page<ProductResponse> getExpiringSoon(Pageable pageable);

}