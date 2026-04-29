package com.kiosco.product_service.service;

import com.kiosco.product_service.client.TenantClient;
import com.kiosco.product_service.dto.ApiResponse;
import com.kiosco.product_service.dto.response.BranchResponse;
import com.kiosco.product_service.dto.response.StoreResponse;
import com.kiosco.product_service.exeption.BusinessException;
import com.kiosco.product_service.exeption.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TenantValidatorService {

    private final TenantClient tenantClient;

    public void validateStoreAndBranch(Long storeId, Long branchId) {
        ApiResponse<StoreResponse> storeResp = tenantClient.getStoreById(storeId);
        if (storeResp.getData() == null || !Boolean.TRUE.equals(storeResp.getData().getActive())) {
            throw new NotFoundException("Store not found or inactive with ID: " + storeId);
        }

        ApiResponse<BranchResponse> branchResp = tenantClient.getBranchById(storeId, branchId);
        if (branchResp.getData() == null || !Boolean.TRUE.equals(branchResp.getData().getActive())) {
            throw new NotFoundException("Branch not found or inactive with ID: " + branchId);
        }

        if (!branchResp.getData().getStoreId().equals(storeId)) {
            throw new BusinessException("Branch does not belong to store " + storeId);
        }
    }
}
