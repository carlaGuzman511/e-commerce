package com.kiosco.product_service.command.product;

import com.kiosco.product_service.context.TenantContext;
import com.kiosco.product_service.command.spec.SafeAbstractCommand;
import com.kiosco.product_service.constants.Messages;
import com.kiosco.product_service.dto.request.StockUpdateRequest;
import com.kiosco.product_service.exeption.BusinessException;
import com.kiosco.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class RestoreStockCmd extends SafeAbstractCommand<List<StockUpdateRequest>, Void> {
    private final ProductService productService;

    @Override
    protected void preExecute(List<StockUpdateRequest> items) {
        if (items == null || items.isEmpty()) {
            throw new BusinessException(Messages.STOCK_EMPTY_LIST);
        }

        for (StockUpdateRequest item : items) {
            if (item.getBranchId() == null || item.getVariantId() == null || item.getQuantity() == null) {
                throw new BusinessException(Messages.STOCK_INVALID);
            }
            if (item.getQuantity() <= 0) {
                throw new BusinessException(Messages.STOCK_QUANTITY_INVALID);
            }
        }
    }

    @Override
    protected Void safeExecute(List<StockUpdateRequest> items) {
        String tenantId = TenantContext.get();
        productService.restoreStock(tenantId, items);
        return null;
    }

    @Override
    protected void postExecute(Void result) {
        log.debug("Stock restore command completed successfully.");
    }
}
