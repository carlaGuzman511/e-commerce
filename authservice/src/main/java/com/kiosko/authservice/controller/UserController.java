package com.kiosko.authservice.controller;

import com.kiosko.authservice.dto.request.AssignPermissionRequest;
import com.kiosko.authservice.dto.request.AssignRoleRequest;
import com.kiosko.authservice.dto.request.CreateUserRequest;
import com.kiosko.authservice.dto.request.UpdateUserRequest;
import com.kiosko.authservice.dto.response.ApiResponse;
import com.kiosko.authservice.dto.response.PageResponse;
import com.kiosko.authservice.dto.response.UserDetailResponse;
import com.kiosko.authservice.dto.response.UserListResponse;
import com.kiosko.authservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<UserListResponse>>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        log.info("Get all users - page: {}, size: {}", page, size);

        PageResponse<UserListResponse> users = userService.getAllUsers(page, size, sortBy, direction);

        return ResponseEntity.ok(
                ApiResponse.success("Users retrieved successfully", users, HttpStatus.OK.value())
        );
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<PageResponse<UserListResponse>>> searchUsers(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        log.info("Search users with query: {}", query);

        PageResponse<UserListResponse> users = userService.searchUsers(query, page, size);

        return ResponseEntity.ok(
                ApiResponse.success("Search completed successfully", users, HttpStatus.OK.value())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDetailResponse>> getUserById(@PathVariable Long id) {
        log.info("Get user by id: {}", id);

        UserDetailResponse user = userService.getUserById(id);

        return ResponseEntity.ok(
                ApiResponse.success("User retrieved successfully", user, HttpStatus.OK.value())
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserDetailResponse>> createUser(
            @Valid @RequestBody CreateUserRequest request
    ) {
        log.info("Create user request: {}", request.getEmail());

        UserDetailResponse user = userService.createUser(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("User created successfully", user, HttpStatus.CREATED.value()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDetailResponse>> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest request
    ) {
        log.info("Update user request: {}", id);

        UserDetailResponse user = userService.updateUser(id, request);

        return ResponseEntity.ok(
                ApiResponse.success("User updated successfully", user, HttpStatus.OK.value())
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        log.info("Delete user request: {}", id);

        userService.deleteUser(id);

        return ResponseEntity.ok(
                ApiResponse.success("User deleted successfully", null, HttpStatus.OK.value())
        );
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<UserDetailResponse>> toggleUserStatus(
            @PathVariable Long id,
            @RequestParam boolean active
    ) {
        log.info("Toggle user status: {} to {}", id, active);

        UserDetailResponse user = userService.toggleUserStatus(id, active);

        return ResponseEntity.ok(
                ApiResponse.success("User status updated successfully", user, HttpStatus.OK.value())
        );
    }

    @PostMapping("/{id}/roles")
    public ResponseEntity<ApiResponse<UserDetailResponse>> assignRoles(
            @PathVariable Long id,
            @Valid @RequestBody AssignRoleRequest request
    ) {
        log.info("Assign roles to user: {}", id);

        UserDetailResponse user = userService.assignRoles(id, request);

        return ResponseEntity.ok(
                ApiResponse.success("Roles assigned successfully", user, HttpStatus.OK.value())
        );
    }

    @DeleteMapping("/{userId}/roles/{roleId}")
    public ResponseEntity<ApiResponse<UserDetailResponse>> removeRole(
            @PathVariable Long userId,
            @PathVariable Long roleId
    ) {
        log.info("Remove role {} from user {}", roleId, userId);

        UserDetailResponse user = userService.removeRole(userId, roleId);

        return ResponseEntity.ok(
                ApiResponse.success("Role removed successfully", user, HttpStatus.OK.value())
        );
    }

    @PostMapping("/{id}/permissions")
    public ResponseEntity<ApiResponse<UserDetailResponse>> assignPermissions(
            @PathVariable Long id,
            @Valid @RequestBody AssignPermissionRequest request
    ) {
        log.info("Assign permissions to user: {}", id);

        UserDetailResponse user = userService.assignPermissions(id, request);

        return ResponseEntity.ok(
                ApiResponse.success("Permissions assigned successfully", user, HttpStatus.OK.value())
        );
    }

    @DeleteMapping("/{userId}/permissions/{permissionId}")
    public ResponseEntity<ApiResponse<UserDetailResponse>> removePermission(
            @PathVariable Long userId,
            @PathVariable Long permissionId
    ) {
        log.info("Remove permission {} from user {}", permissionId, userId);

        UserDetailResponse user = userService.removePermission(userId, permissionId);

        return ResponseEntity.ok(
                ApiResponse.success("Permission removed successfully", user, HttpStatus.OK.value())
        );
    }
}