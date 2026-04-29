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
public class CreateBranchController {
    private final CreateBranchService createBranchService;
    private Long storeId;

    @ModelAttribute
    public void setStoreId(@PathVariable Long storeId) {
        this.storeId = storeId;
    }

    public CreateBranchController(CreateBranchService createBranchService) {
        this.createBranchService = createBranchService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BranchResponse>> create(
            @Valid @RequestBody BranchRequest request) {
        BranchResponse resp = createBranchService.create(storeId, request);

        ApiResponse<BranchResponse> response = ApiResponse.<BranchResponse>builder()
                .timestamp(Instant.now())
                .message(Messages.BRANCH_CREATED)
                .status(201)
                .data(resp)
                .build();

        return ResponseEntity.status(201).body(response);
    }
}
