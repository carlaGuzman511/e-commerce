package com.kiosco.product_service.command.product;

import com.kiosco.product_service.context.TenantContext;
import com.kiosco.product_service.command.spec.SafeAbstractCommand;
import com.kiosco.product_service.constants.Messages;
import com.kiosco.product_service.dto.request.*;
import com.kiosco.product_service.dto.response.ProductResponse;
import com.kiosco.product_service.exeption.BusinessException;
import com.kiosco.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateProductCmd  extends SafeAbstractCommand<ProductUpdateRequest, ProductResponse> {
    private final ProductService productService;

    @Override
    protected void preExecute(ProductUpdateRequest request) {
        if (request.getVariants() == null || request.getVariants().isEmpty()) {
            throw new BusinessException(Messages.PRODUCT_VARIANT_REQUIRED);
        }

        for (ProductVariantUpdateRequest variantCreateRequest : request.getVariants()) {
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
    }

    @Override
    protected ProductResponse safeExecute(ProductUpdateRequest request) {
        String tenantId = TenantContext.get();
        return productService.update(tenantId, request);
    }

    @Override
    protected void postExecute(ProductResponse response) {
        log.info(Messages.LOG_PRODUCT_CREATED, response.getId());
    }
}
