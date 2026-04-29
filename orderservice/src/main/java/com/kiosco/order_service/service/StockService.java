package com.kiosco.order_service.service;

import com.kiosco.order_service.client.ProductFeignClient;
import com.kiosco.order_service.config.TenantContext;
import com.kiosco.order_service.constants.Messages;
import com.kiosco.order_service.domain.Order;
import com.kiosco.order_service.dto.request.CreateOrderRequest;
import com.kiosco.order_service.dto.request.OrderItemRequest;
import com.kiosco.order_service.dto.request.StockCheckRequest;
import com.kiosco.order_service.dto.request.StockUpdateRequest;
import com.kiosco.order_service.dto.response.ApiResponse;
import com.kiosco.order_service.dto.response.StockResponse;
import com.kiosco.order_service.exception.ExternalServiceException;
import com.kiosco.order_service.exception.InsufficientStockException;
import feign.FeignException;
import feign.RetryableException;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StockService {

    private final ProductFeignClient productClient;

    public StockService(ProductFeignClient productClient) {
        this.productClient = productClient;
    }

    public void validateStock(CreateOrderRequest req) {
            try {
                String tenantId = TenantContext.getCurrentTenant();

                for (OrderItemRequest item : req.getItems()) {
                    StockCheckRequest check = StockCheckRequest.builder()
                            .variantId(item.getVariantId())

                            .branchId(req.getBranchId())
                            .requested(item.getQuantity())
                            .build();

                ApiResponse<StockResponse> response = productClient.checkStock(tenantId, check);

                if (response == null || response.getStatus() != HttpStatus.OK.value()) {
                    throw new ExternalServiceException(Messages.FAILED_TO_VERIFY_STOCK + item.getProductId());
                }

                if (!response.getData().getAvailable()) {
                    throw new InsufficientStockException(Messages.INSUFFICIENT_STOCK + item.getProductId());
                }
            }}
            catch (FeignException.FeignClientException e) {
                throw new ExternalServiceException(Messages.INVENTORY_SERVICE_CLIENT + e.contentUTF8());
            }
            catch (FeignException.FeignServerException e) {
                throw new ExternalServiceException(Messages.INVENTORY_SERVICE_INTERNAL + e.contentUTF8());
            }
            catch (RetryableException e) {
                throw new ExternalServiceException(Messages.INVENTORY_SERVICE_UNREACHABLE + e.getMessage());
            }
            catch (Exception e) {
                throw new ExternalServiceException(Messages.UNEXPECTED_ERROR + e.getMessage());
            }
    }

    public void reserveStock(CreateOrderRequest req) {
        try {
            String tenantId = TenantContext.getCurrentTenant();
            List<StockUpdateRequest> updates = req.getItems().stream()
                    .map(item -> StockUpdateRequest.builder()
                            .branchId(req.getBranchId())
                            .variantId(item.getProductId())
                            .quantity(item.getQuantity())
                            .build())
                    .toList();

            ApiResponse<Void> response = productClient.reserveStock(tenantId, updates);

            if (response == null || response.getStatus() != HttpStatus.OK.value()) {
                throw new ExternalServiceException(Messages.FAILED_TO_RESERVE_REQUESTED_STOCK);
            }
        }
        catch (FeignException.FeignClientException e) {
            throw new ExternalServiceException(Messages.INVENTORY_SERVICE_CLIENT + e.contentUTF8());
        }
        catch (FeignException.FeignServerException e) {
            throw new ExternalServiceException(Messages.INVENTORY_SERVICE_INTERNAL + e.contentUTF8());
        }
        catch (RetryableException e) {
            throw new ExternalServiceException(Messages.INVENTORY_SERVICE_UNREACHABLE + e.getMessage());
        }
        catch (Exception e) {
            throw new ExternalServiceException(Messages.UNEXPECTED_ERROR + e.getMessage());
        }
    }

    public void restoreStock(Order order) {
        try {
            String tenantId = TenantContext.getCurrentTenant();
            List<StockUpdateRequest> updates = order.getItems().stream()
                    .map(item -> StockUpdateRequest.builder()
                            .branchId(order.getBranchId())
                            .variantId(item.getProductId())
                            .quantity(item.getQuantity())
                            .build())
                    .toList();

            ApiResponse<Void> response = productClient.reserveStock(tenantId, updates);

            if (response == null || response.getStatus() != HttpStatus.OK.value()) {
                throw new ExternalServiceException(Messages.FAILED_TO_RESERVE_REQUESTED_STOCK);
            }
        }
        catch (FeignException.FeignClientException e) {
            throw new ExternalServiceException(Messages.INVENTORY_SERVICE_CLIENT + e.contentUTF8());
        }
        catch (FeignException.FeignServerException e) {
            throw new ExternalServiceException(Messages.INVENTORY_SERVICE_INTERNAL + e.contentUTF8());
        }
        catch (RetryableException e) {
            throw new ExternalServiceException(Messages.INVENTORY_SERVICE_UNREACHABLE + e.getMessage());
        }
        catch (Exception e) {
            throw new ExternalServiceException(Messages.UNEXPECTED_ERROR + e.getMessage());
        }
    }
}