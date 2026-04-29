package com.kiosco.product_service.command.product;

import com.kiosco.product_service.context.TenantContext;
import com.kiosco.product_service.command.spec.SafeAbstractCommand;
import com.kiosco.product_service.dto.response.ProductResponse;
import com.kiosco.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetProductByIdCmd extends SafeAbstractCommand<Long, ProductResponse> {
    private final ProductService productService;

    @Override
    protected ProductResponse safeExecute(Long productId) {
        String tenantId = TenantContext.get();
        return productService.getById(tenantId, productId);
    }
}