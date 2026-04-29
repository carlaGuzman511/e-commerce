package com.kiosco.order_service.client;

import com.kiosco.order_service.constants.ApiPath;
import com.kiosco.order_service.dto.request.StockCheckRequest;
import com.kiosco.order_service.dto.request.StockUpdateRequest;
import com.kiosco.order_service.dto.response.ApiResponse;
import com.kiosco.order_service.dto.response.StockResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@FeignClient(name = "products-service", url = "${spring.services.products.base-url}")
public interface ProductFeignClient {
    @PostMapping(ApiPath.Product.BRANCH_STOCK_CHECK)
    public ApiResponse<StockResponse> checkStock(@RequestHeader("X-Tenant-Id") String tenantId, @RequestBody StockCheckRequest request);

    @PostMapping(ApiPath.Product.BRANCH_STOCK_RESERVE)
    public ApiResponse<Void> reserveStock(@RequestHeader("X-Tenant-Id") String tenantId, @RequestBody List<StockUpdateRequest> items);

    @PostMapping(ApiPath.Product.BRANCH_STOCK_RESTORE)
    public ApiResponse<Void> restoreStock(@RequestHeader("X-Tenant-Id") String tenantId, @RequestBody List<StockUpdateRequest> items);
}