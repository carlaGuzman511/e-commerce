package com.kiosko.authservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimpleAuthResponse {

    private String accessToken;
    private String refreshToken;
    private Long expiresIn;
    private SimpleUserResponse user;
}