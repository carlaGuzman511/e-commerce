package com.kiosco.tenant_service.controller.branch;

import com.kiosco.tenant_service.constants.ApiPath;
import com.kiosco.tenant_service.constants.Messages;
import com.kiosco.tenant_service.dto.request.BranchRequest;
import com.kiosco.tenant_service.dto.response.ApiResponse;
import com.kiosco.tenant_service.dto.response.BranchResponse;
import com.kiosco.tenant_service.service.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;

@RestController
@RequestMapping(ApiPath.Branch.BASE)
public class UpdateBranchController {
    private final UpdateBranchService updateBranchService;
    private Long storeId;

    @ModelAttribute
    public void setStoreId(@PathVariable Long storeId) {
        this.storeId = storeId;
    }

    public UpdateBranchController(UpdateBranchService updateBranchService) {
        this.updateBranchService = updateBranchService;
    }

    @PutMapping(ApiPath.Branch.BY_ID)
    public ResponseEntity<ApiResponse<BranchResponse>> update(
            @PathVariable Long branchId,
            @Valid @RequestBody BranchRequest request) {
        BranchResponse resp = updateBranchService.  update(storeId, branchId, request);

        ApiResponse<BranchResponse> response = ApiResponse.<BranchResponse>builder()
                .timestamp(Instant.now())
                .message(Messages.BRANCH_UPDATED)
                .status(200)
                .data(resp)
                .build();

        return ResponseEntity.ok(response);
    }
}