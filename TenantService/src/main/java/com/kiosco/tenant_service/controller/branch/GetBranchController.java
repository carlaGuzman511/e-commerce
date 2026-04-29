package com.kiosco.tenant_service.controller.branch;

import com.kiosco.tenant_service.constants.ApiPath;
import com.kiosco.tenant_service.constants.Messages;
import com.kiosco.tenant_service.dto.response.ApiResponse;
import com.kiosco.tenant_service.dto.response.BranchResponse;
import com.kiosco.tenant_service.service.GetBranchService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

@RestController
@RequestMapping(ApiPath.Branch.BASE)
public class GetBranchController {
    private final GetBranchService getBranchService;
    private Long storeId;

    @ModelAttribute
    public void setStoreId(@PathVariable Long storeId) {
        this.storeId = storeId;
    }

    public GetBranchController(GetBranchService getBranchService) {
        this.getBranchService = getBranchService;
    }


    @GetMapping(ApiPath.Branch.BY_ID)
    public ResponseEntity<ApiResponse<BranchResponse>> get(
            @PathVariable Long branchId) {
        BranchResponse resp = getBranchService.getById(storeId, branchId);
        ApiResponse<BranchResponse> response = ApiResponse.<BranchResponse>builder()
                .timestamp(Instant.now())
                .message(Messages.BRANCH_RETRIEVED)
                .status(200)
                .data(resp)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<BranchResponse>>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDate) {
        Page<BranchResponse> pagedBranchResponse = getBranchService.getAll(storeId,
                PageRequest.of(page, size),
                active,
                startDate != null ? startDate.atStartOfDay().toInstant(ZoneOffset.UTC) : null,
                endDate != null ? endDate.plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC) : null);

        ApiResponse<Page<BranchResponse>> response = ApiResponse.<Page<BranchResponse>>builder().
                timestamp(Instant.now())
                .message(Messages.BRANCH_RETRIEVED)
                .status(200)
                .data(pagedBranchResponse)
                .build();

        return ResponseEntity.ok(response);
    }
}
