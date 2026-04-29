package com.kiosko.authservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {

    @Email(message = "Email must be valid")
    private String email;

    @Size(min = 3, max = 100, message = "Username must be between 3 and 100 characters")
    private String username;

    private String tenantId;

    private Boolean active;

    private Boolean emailVerified;
}