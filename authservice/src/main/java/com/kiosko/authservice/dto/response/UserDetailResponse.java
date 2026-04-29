package com.kiosko.authservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailResponse {

    private Long id;
    private String email;
    private String username;
    private String image;
    private Boolean active;
    private Boolean emailVerified;
    private String tenantId;
    private String authProvider;
    private Integer failedLoginAttempts;
    private LocalDateTime lastLoginAt;
    private Set<RoleInfo> roles;
    private Set<PermissionInfo> directPermissions;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RoleInfo {
        private Long id;
        private String name;
        private String description;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PermissionInfo {
        private Long id;
        private String resource;
        private String action;
        private String description;
    }
}