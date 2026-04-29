package com.kiosko.authservice.controller;

import com.kiosko.authservice.dto.request.AssignPermissionsToRoleRequest;
import com.kiosko.authservice.dto.request.CreateRoleRequest;
import com.kiosko.authservice.dto.request.UpdateRoleRequest;
import com.kiosko.authservice.dto.response.ApiResponse;
import com.kiosko.authservice.dto.response.PageResponse;
import com.kiosko.authservice.dto.response.RoleDetailResponse;
import com.kiosko.authservice.dto.response.RoleListResponse;
import com.kiosko.authservice.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
@Slf4j
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<PageResponse<RoleListResponse>>> getAllRoles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        log.info("Get all roles - page: {}, size: {}", page, size);

        PageResponse<RoleListResponse> roles = roleService.getAllRoles(page, size, sortBy, direction);

        return ResponseEntity.ok(
                ApiResponse.success("Roles retrieved successfully", roles, HttpStatus.OK.value())
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<RoleDetailResponse>> getRoleById(@PathVariable Long id) {
        log.info("Get role by id: {}", id);

        RoleDetailResponse role = roleService.getRoleById(id);

        return ResponseEntity.ok(
                ApiResponse.success("Role retrieved successfully", role, HttpStatus.OK.value())
        );
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<RoleDetailResponse>> createRole(
            @Valid @RequestBody CreateRoleRequest request
    ) {
        log.info("Create role request: {}", request.getName());

        RoleDetailResponse role = roleService.createRole(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Role created successfully", role, HttpStatus.CREATED.value()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<RoleDetailResponse>> updateRole(
            @PathVariable Long id,
            @Valid @RequestBody UpdateRoleRequest request
    ) {
        log.info("Update role request: {}", id);

        RoleDetailResponse role = roleService.updateRole(id, request);

        return ResponseEntity.ok(
                ApiResponse.success("Role updated successfully", role, HttpStatus.OK.value())
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteRole(@PathVariable Long id) {
        log.info("Delete role request: {}", id);

        roleService.deleteRole(id);

        return ResponseEntity.ok(
                ApiResponse.success("Role deleted successfully", null, HttpStatus.OK.value())
        );
    }

    @PostMapping("/{id}/permissions")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<RoleDetailResponse>> assignPermissions(
            @PathVariable Long id,
            @Valid @RequestBody AssignPermissionsToRoleRequest request
    ) {
        log.info("Assign permissions to role: {}", id);

        RoleDetailResponse role = roleService.assignPermissions(id, request);

        return ResponseEntity.ok(
                ApiResponse.success("Permissions assigned successfully", role, HttpStatus.OK.value())
        );
    }

    @DeleteMapping("/{roleId}/permissions/{permissionId}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<RoleDetailResponse>> removePermission(
            @PathVariable Long roleId,
            @PathVariable Long permissionId
    ) {
        log.info("Remove permission {} from role {}", permissionId, roleId);

        RoleDetailResponse role = roleService.removePermission(roleId, permissionId);

        return ResponseEntity.ok(
                ApiResponse.success("Permission removed successfully", role, HttpStatus.OK.value())
        );
    }

    @GetMapping("/{id}/users")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<RoleDetailResponse>> getRoleWithUsers(@PathVariable Long id) {
        log.info("Get role with users: {}", id);

        RoleDetailResponse role = roleService.getRoleWithUsers(id);

        return ResponseEntity.ok(
                ApiResponse.success("Role with users retrieved successfully", role, HttpStatus.OK.value())
        );
    }
}