package com.kiosco.tenant_service.controller.store;

import com.kiosco.tenant_service.constants.ApiPath;
import com.kiosco.tenant_service.constants.Messages;
import com.kiosco.tenant_service.dto.request.StoreRequest;
import com.kiosco.tenant_service.dto.response.ApiResponse;
import com.kiosco.tenant_service.dto.response.StoreResponse;
import com.kiosco.tenant_service.service.UpdateStoreService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;

@RestController
@RequestMapping(ApiPath.Store.BASE)
public class UpdateStoreController {
    private final UpdateStoreService updateStoreService;

    public UpdateStoreController(UpdateStoreService updateStoreService) {
        this.updateStoreService = updateStoreService;
    }

    @PutMapping(ApiPath.Store.BY_ID)
    public ResponseEntity<ApiResponse<StoreResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody StoreRequest request) {
        StoreResponse resp = updateStoreService.update(id, request);
        ApiResponse<StoreResponse> response = ApiResponse.<StoreResponse>builder()
                .timestamp(Instant.now())
                .message(Messages.STORE_UPDATED)
                .status(200)
                .data(resp)
                .build();

        return ResponseEntity.ok(response);
    }
}