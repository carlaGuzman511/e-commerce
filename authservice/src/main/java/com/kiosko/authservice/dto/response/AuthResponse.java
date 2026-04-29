package com.kiosko.authservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private JwtTokenInfo accessToken;
    private JwtTokenInfo refreshToken;
    private String tokenType;
    private Long expiresIn;
    private UserResponse user;

    public AuthResponse(JwtTokenInfo accessToken, JwtTokenInfo refreshToken, Long expiresIn, UserResponse user) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokenType = "Bearer";
        this.expiresIn = expiresIn;
        this.user = user;
    }
}