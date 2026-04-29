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
public class UserResponse {

    private Long id;
    private String email;
    private String username;
    private String image;
    private Boolean active;
    private Boolean emailVerified;
    private String tenantId;
    private Set<String> roles;
    private Set<String> permissions;
    private LocalDateTime createdAt;
}