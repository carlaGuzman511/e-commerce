package com.kiosco.product_service.command.product;

import com.kiosco.product_service.context.TenantContext;
import com.kiosco.product_service.command.spec.SafeAbstractCommand;
import com.kiosco.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteProductCmd extends SafeAbstractCommand<Long, Boolean> {
    private final ProductService productService;

    @Override
    protected Boolean safeExecute(Long id) {
        String tenantId = TenantContext.get();
        return productService.delete(id, tenantId);
    }
}
