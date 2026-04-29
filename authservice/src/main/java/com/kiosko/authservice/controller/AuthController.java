package com.kiosko.authservice.controller;

import com.kiosko.authservice.dto.request.LoginRequest;
import com.kiosko.authservice.dto.request.RefreshTokenRequest;
import com.kiosko.authservice.dto.request.RegisterRequest;
import com.kiosko.authservice.dto.response.ApiResponse;
import com.kiosko.authservice.dto.response.SimpleAuthResponse;
import com.kiosko.authservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<SimpleAuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        log.info("Login request received for email: {}", request.getEmail());

        SimpleAuthResponse response = authService.login(request);

        return ResponseEntity.ok(
                ApiResponse.success("Login successful", response, HttpStatus.OK.value())
        );
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<SimpleAuthResponse>> register(@Valid @RequestBody RegisterRequest request) {
        log.info("Registration request received for email: {}", request.getEmail());

        SimpleAuthResponse response = authService.register(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("User registered successfully", response, HttpStatus.CREATED.value()));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<SimpleAuthResponse>> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        log.info("Refresh token request received");

        SimpleAuthResponse response = authService.refreshToken(request);

        return ResponseEntity.ok(
                ApiResponse.success("Token refreshed successfully", response, HttpStatus.OK.value())
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@Valid @RequestBody RefreshTokenRequest request) {
        log.info("Logout request received");

        authService.logout(request.getRefreshToken());

        return ResponseEntity.ok(
                ApiResponse.success("Logout successful", null, HttpStatus.OK.value())
        );
    }

    @GetMapping("/health")
    public ResponseEntity<ApiResponse<Map<String, Object>>> health() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("service", "AuthService");
        health.put("version", "1.0.0");
        health.put("timestamp", LocalDateTime.now());

        return ResponseEntity.ok(
                ApiResponse.success("Auth Service is running", health, HttpStatus.OK.value())
        );
    }
}