package com.kiosko.authservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionListResponse {

    private Long id;
    private String resource;
    private String action;
    private String fullPermission;
    private String description;
    private Integer rolesCount;
    private LocalDateTime createdAt;
}