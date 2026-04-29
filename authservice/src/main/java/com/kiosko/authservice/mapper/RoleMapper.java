package com.kiosko.authservice.mapper;

import com.kiosko.authservice.domain.entity.Role;
import com.kiosko.authservice.dto.response.RoleDetailResponse;
import com.kiosko.authservice.dto.response.RoleListResponse;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RoleMapper {

    public RoleListResponse toRoleListResponse(Role role) {
        return RoleListResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .userCount(role.getUsers() != null ? role.getUsers().size() : 0)
                .permissionCount(role.getPermissions() != null ? role.getPermissions().size() : 0)
                .createdAt(role.getCreatedAt())
                .build();
    }

    public RoleDetailResponse toRoleDetailResponse(Role role) {
        return RoleDetailResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .tenantId(role.getTenantId())
                .userCount(role.getUsers() != null ? role.getUsers().size() : 0)
                .permissions(role.getPermissions().stream()
                        .map(permission -> RoleDetailResponse.PermissionInfo.builder()
                                .id(permission.getId())
                                .resource(permission.getResource())
                                .action(permission.getAction())
                                .fullPermission(permission.getFullPermission())
                                .description(permission.getDescription())
                                .build())
                        .collect(Collectors.toSet()))
                .createdAt(role.getCreatedAt())
                .updatedAt(role.getUpdatedAt())
                .build();
    }
}