package com.kiosko.authservice.mapper;

import com.kiosko.authservice.domain.entity.Permission;
import com.kiosko.authservice.dto.response.PermissionDetailResponse;
import com.kiosko.authservice.dto.response.PermissionListResponse;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PermissionMapper {

    public PermissionListResponse toPermissionListResponse(Permission permission) {
        return PermissionListResponse.builder()
                .id(permission.getId())
                .resource(permission.getResource())
                .action(permission.getAction())
                .fullPermission(permission.getFullPermission())
                .description(permission.getDescription())
                .rolesCount(permission.getRoles() != null ? permission.getRoles().size() : 0)
                .createdAt(permission.getCreatedAt())
                .build();
    }

    public PermissionDetailResponse toPermissionDetailResponse(Permission permission) {
        return PermissionDetailResponse.builder()
                .id(permission.getId())
                .resource(permission.getResource())
                .action(permission.getAction())
                .fullPermission(permission.getFullPermission())
                .description(permission.getDescription())
                .rolesCount(permission.getRoles() != null ? permission.getRoles().size() : 0)
                .roles(permission.getRoles().stream()
                        .map(role -> PermissionDetailResponse.RoleInfo.builder()
                                .id(role.getId())
                                .name(role.getName())
                                .description(role.getDescription())
                                .build())
                        .collect(Collectors.toSet()))
                .createdAt(permission.getCreatedAt())
                .build();
    }
}