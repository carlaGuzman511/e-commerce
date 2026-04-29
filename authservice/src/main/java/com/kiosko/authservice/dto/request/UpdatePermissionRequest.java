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
public class UpdatePermissionRequest {

    @Size(min = 2, max = 50, message = "Resource must be between 2 and 50 characters")
    @Pattern(regexp = "^[a-z_]+$", message = "Resource must be lowercase letters and underscores only")
    private String resource;

    @Size(min = 2, max = 50, message = "Action must be between 2 and 50 characters")
    @Pattern(regexp = "^[a-z_]+$", message = "Action must be lowercase letters and underscores only")
    private String action;

    @Size(max = 255, message = "Description must be less than 255 characters")
    private String description;
}