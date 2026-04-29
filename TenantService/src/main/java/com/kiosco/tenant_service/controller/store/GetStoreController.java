package com.kiosco.tenant_service.controller.store;

import com.kiosco.tenant_service.constants.ApiPath;
import com.kiosco.tenant_service.constants.Messages;
import com.kiosco.tenant_service.dto.response.ApiResponse;
import com.kiosco.tenant_service.dto.response.StoreResponse;
import com.kiosco.tenant_service.service.GetStoreService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

@RestController
@RequestMapping(ApiPath.Store.BASE)
public class GetStoreController {
    private final GetStoreService getStoreService;

    public GetStoreController(GetStoreService getStoreService) {
        this.getStoreService = getStoreService;
    }

    @GetMapping(ApiPath.Store.BY_ID)
    public ResponseEntity<ApiResponse<StoreResponse>> get(@PathVariable Long id) {
        StoreResponse resp = getStoreService.getById(id);

        ApiResponse<StoreResponse> response = ApiResponse.<StoreResponse>builder()
                .timestamp(Instant.now())
                .status(200)
                .message(Messages.STORE_RETRIEVED)
                .data(resp)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<StoreResponse>>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) String tenantId,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDate) {
        Page<StoreResponse> resp = getStoreService.getAll(
                PageRequest.of(page, size),
                active,
                tenantId,
                startDate != null ? startDate.atStartOfDay().toInstant(ZoneOffset.UTC) : null,
                endDate != null ? endDate.plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC) : null);

        ApiResponse<Page<StoreResponse>> response = ApiResponse.<Page<StoreResponse>>builder()
                .timestamp(Instant.now())
                .message(Messages.STORES_RETRIEVED)
                .status(200)
                .data(resp)
                .build();

        return ResponseEntity.ok(response);
    }
}