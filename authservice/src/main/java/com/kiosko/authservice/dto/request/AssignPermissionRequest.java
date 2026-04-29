package com.kiosko.authservice.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignPermissionRequest {

    @NotEmpty(message = "Permission IDs cannot be empty")
    private Set<Long> permissionIds;
}