package com.kiosco.tenant_service.controller.store;

import com.kiosco.tenant_service.constants.ApiPath;
import com.kiosco.tenant_service.constants.Messages;
import com.kiosco.tenant_service.dto.request.StoreRequest;
import com.kiosco.tenant_service.dto.response.ApiResponse;
import com.kiosco.tenant_service.dto.response.StoreResponse;
import com.kiosco.tenant_service.service.CreateStoreService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;

@RestController
@RequestMapping(ApiPath.Store.BASE)
public class CreateStoreController {
    private final CreateStoreService createStoreService;

    public CreateStoreController(CreateStoreService createStoreService) {
        this.createStoreService = createStoreService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<StoreResponse>> create(
            @Valid @RequestBody StoreRequest request) {
        StoreResponse resp = createStoreService.create(request);

        ApiResponse<StoreResponse> response = ApiResponse.<StoreResponse>builder()
                .timestamp(Instant.now())
                .status(201)
                .message(Messages.STORE_CREATED)
                .data(resp)
                .build();

        return ResponseEntity.status(201).body(response);
    }
}