package com.kiosco.product_service.client;

import com.kiosco.product_service.constants.ApiPaths;
import com.kiosco.product_service.dto.ApiResponse;
import com.kiosco.product_service.dto.response.BranchResponse;
import com.kiosco.product_service.dto.response.StoreResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "tenant-service",
        url = "${services.tenants.base-url}"
)
public interface TenantClient {

    @GetMapping(ApiPaths.Tenant.STORE_BY_ID)
    ApiResponse<StoreResponse> getStoreById(@PathVariable("storeId") Long storeId);

    @GetMapping(ApiPaths.Tenant.BRANCH_BY_ID)
    ApiResponse<BranchResponse> getBranchById(@PathVariable("storeId") Long storeId,
                                              @PathVariable("branchId") Long branchId);
}
