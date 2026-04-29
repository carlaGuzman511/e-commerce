package com.kiosco.product_service.service;

import com.kiosco.product_service.dto.request.ProductCreateRequest;
import com.kiosco.product_service.dto.request.ProductUpdateRequest;
import com.kiosco.product_service.dto.request.StockCheckRequest;
import com.kiosco.product_service.dto.request.StockUpdateRequest;
import com.kiosco.product_service.dto.response.ProductResponse;
import com.kiosco.product_service.dto.response.StockResponse;

import java.util.List;

public interface ProductService {

    ProductResponse create(String tenantIdHeader, ProductCreateRequest request);

    boolean existsByNameAndTenant(String tenantIdHeader, String name);

    ProductResponse getById(String tenantId, Long productId);

    void reserveStock(String tenantId, List<StockUpdateRequest> items);

    void restoreStock(String tenantId, List<StockUpdateRequest> items);

    StockResponse getStock(String tenantId, StockCheckRequest request);

    ProductResponse update(String tenantId, ProductUpdateRequest request);

    boolean delete(Long id, String tenantId);
}
