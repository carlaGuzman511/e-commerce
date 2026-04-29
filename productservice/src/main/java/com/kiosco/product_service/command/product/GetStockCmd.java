package com.kiosco.product_service.command.product;

import com.kiosco.product_service.context.TenantContext;
import com.kiosco.product_service.command.spec.SafeAbstractCommand;
import com.kiosco.product_service.constants.Messages;
import com.kiosco.product_service.dto.request.StockCheckRequest;
import com.kiosco.product_service.dto.response.StockResponse;
import com.kiosco.product_service.exeption.BusinessException;
import com.kiosco.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class GetStockCmd extends SafeAbstractCommand<StockCheckRequest, StockResponse> {

    private final ProductService productService;

    @Override
    protected void preExecute(StockCheckRequest request) {
        if (request == null) {
            throw new BusinessException(Messages.STOCK_INVALID);
        }

        if (request.getBranchId() == null) {
            throw new BusinessException(Messages.BRANCH_NOT_FOUND);
        }

        if (request.getVariantId() == null) {
            throw new BusinessException(Messages.VARIANT_ID_REQUIRED);
        }

        if (request.getRequested() != null && request.getRequested() < 0) {
            throw new BusinessException(Messages.STOCK_STOCK_NEGATIVE);
        }
    }

    @Override
    protected StockResponse safeExecute(StockCheckRequest request) {
        String tenantId = TenantContext.get();
        StockResponse response = productService.getStock(tenantId, request);
        log.info("Checked stock for variant {} in branch {} (tenant {}).",
                request.getVariantId(), request.getBranchId(), tenantId);
        return response;
    }

    @Override
    protected void postExecute(StockResponse response) {
        log.debug("Stock check command completed. Available: {}", response.getAvailable());
    }
}
