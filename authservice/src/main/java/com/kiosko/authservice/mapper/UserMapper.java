package com.kiosko.authservice.mapper;

import com.kiosko.authservice.domain.entity.Role;
import com.kiosko.authservice.domain.entity.User;
import com.kiosko.authservice.dto.response.SimpleUserResponse;
import com.kiosko.authservice.dto.response.UserResponse;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserResponse toUserResponse(User user) {
        Set<String> allPermissions = new HashSet<>();

        user.getRoles().forEach(role -> {
            role.getPermissions().forEach(permission -> {
                allPermissions.add(permission.getFullPermission());
            });
        });

        user.getPermissions().forEach(permission -> {
            allPermissions.add(permission.getFullPermission());
        });

        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .image(user.getImage())
                .active(user.getActive())
                .emailVerified(user.getEmailVerified())
                .tenantId(user.getTenantId())
                .roles(user.getRoles().stream()
                        .map(Role::getName)
                        .collect(Collectors.toSet()))
                .permissions(allPermissions)
                .createdAt(user.getCreatedAt())
                .build();
    }

    public SimpleUserResponse toSimpleUserResponse(User user) {
        return SimpleUserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .tenantId(user.getTenantId())
                .username(user.getUsername())
                .image(user.getImage())
                .roles(user.getRoles().stream()
                        .map(Role::getName)
                        .collect(Collectors.toSet()))
                .build();
    }
}