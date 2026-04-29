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
public class UserListResponse {

    private Long id;
    private String email;
    private String tenantId;
    private String username;
    private Boolean active;
    private Boolean emailVerified;
    private Set<String> roles;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;
}