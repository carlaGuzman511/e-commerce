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
public class PermissionDetailResponse {

    private Long id;
    private String resource;
    private String action;
    private String fullPermission;
    private String description;
    private Integer rolesCount;
    private Set<RoleInfo> roles;
    private LocalDateTime createdAt;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RoleInfo {
        private Long id;
        private String name;
        private String description;
    }
}