package com.kiosko.authservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimpleUserResponse {

    private Long id;
    private String email;
    private String tenantId;
    private String username;
    private String image;
    private Set<String> roles;
}