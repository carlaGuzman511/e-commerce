package com.kiosko.authservice.controller;

import com.kiosko.authservice.dto.request.CreatePermissionRequest;
import com.kiosko.authservice.dto.request.UpdatePermissionRequest;
import com.kiosko.authservice.dto.response.ApiResponse;
import com.kiosko.authservice.dto.response.PageResponse;
import com.kiosko.authservice.dto.response.PermissionDetailResponse;
import com.kiosko.authservice.dto.response.PermissionListResponse;
import com.kiosko.authservice.service.PermissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/permissions")
@RequiredArgsConstructor
@Slf4j
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class PermissionController {

    private final PermissionService permissionService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<PermissionListResponse>>> getAllPermissions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "resource") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        log.info("Get all permissions - page: {}, size: {}", page, size);

        PageResponse<PermissionListResponse> permissions = permissionService.getAllPermissions(page, size, sortBy, direction);

        return ResponseEntity.ok(
                ApiResponse.success("Permissions retrieved successfully", permissions, HttpStatus.OK.value())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PermissionDetailResponse>> getPermissionById(@PathVariable Long id) {
        log.info("Get permission by id: {}", id);

        PermissionDetailResponse permission = permissionService.getPermissionById(id);

        return ResponseEntity.ok(
                ApiResponse.success("Permission retrieved successfully", permission, HttpStatus.OK.value())
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PermissionDetailResponse>> createPermission(
            @Valid @RequestBody CreatePermissionRequest request
    ) {
        log.info("Create permission request: {}:{}", request.getResource(), request.getAction());

        PermissionDetailResponse permission = permissionService.createPermission(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Permission created successfully", permission, HttpStatus.CREATED.value()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PermissionDetailResponse>> updatePermission(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePermissionRequest request
    ) {
        log.info("Update permission request: {}", id);

        PermissionDetailResponse permission = permissionService.updatePermission(id, request);

        return ResponseEntity.ok(
                ApiResponse.success("Permission updated successfully", permission, HttpStatus.OK.value())
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePermission(@PathVariable Long id) {
        log.info("Delete permission request: {}", id);

        permissionService.deletePermission(id);

        return ResponseEntity.ok(
                ApiResponse.success("Permission deleted successfully", null, HttpStatus.OK.value())
        );
    }

    @GetMapping("/by-resource")
    public ResponseEntity<ApiResponse<List<PermissionListResponse>>> getPermissionsByResource(
            @RequestParam String resource
    ) {
        log.info("Get permissions by resource: {}", resource);

        List<PermissionListResponse> permissions = permissionService.getPermissionsByResource(resource);

        return ResponseEntity.ok(
                ApiResponse.success("Permissions retrieved successfully", permissions, HttpStatus.OK.value())
        );
    }
}