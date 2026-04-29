package com.kiosko.authservice.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRoleRequest {

    @Size(min = 2, max = 50, message = "Role name must be between 2 and 50 characters")
    @Pattern(regexp = "^[A-Z_]+$", message = "Role name must be uppercase letters and underscores only (e.g., ADMIN, SUPER_ADMIN)")
    private String name;

    @Size(max = 255, message = "Description must be less than 255 characters")
    private String description;

    private String tenantId;
}