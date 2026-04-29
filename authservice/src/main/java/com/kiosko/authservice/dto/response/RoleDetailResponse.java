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
public class RoleDetailResponse {

    private Long id;
    private String name;
    private String description;
    private String tenantId;
    private Integer userCount;
    private Set<PermissionInfo> permissions;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PermissionInfo {
        private Long id;
        private String resource;
        private String action;
        private String fullPermission;
        private String description;
    }
}