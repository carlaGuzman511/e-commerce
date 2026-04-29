package com.kiosko.authservice.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProfileRequest {

    @Size(min = 3, max = 100, message = "Username must be between 3 and 100 characters")
    private String username;

    @Size(max = 500, message = "Image URL must be less than 500 characters")
    private String image;
}