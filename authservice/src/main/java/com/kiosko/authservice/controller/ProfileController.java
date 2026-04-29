package com.kiosko.authservice.controller;

import com.kiosko.authservice.dto.request.ChangePasswordRequest;
import com.kiosko.authservice.dto.request.UpdateProfileRequest;
import com.kiosko.authservice.dto.response.ApiResponse;
import com.kiosko.authservice.dto.response.SimpleUserResponse;
import com.kiosko.authservice.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
@Slf4j
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<SimpleUserResponse>> getProfile(Authentication authentication) {
        log.info("Get profile for user: {}", authentication.getName());

        SimpleUserResponse user = profileService.getProfile(authentication.getName());

        return ResponseEntity.ok(
                ApiResponse.success("Profile retrieved successfully", user, HttpStatus.OK.value())
        );
    }

    @PutMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<SimpleUserResponse>> updateProfile(
            Authentication authentication,
            @Valid @RequestBody UpdateProfileRequest request
    ) {
        log.info("Update profile for user: {}", authentication.getName());

        SimpleUserResponse user = profileService.updateProfile(authentication.getName(), request);

        return ResponseEntity.ok(
                ApiResponse.success("Profile updated successfully", user, HttpStatus.OK.value())
        );
    }

    @PutMapping("/password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<Void>> changePassword(
            Authentication authentication,
            @Valid @RequestBody ChangePasswordRequest request
    ) {
        log.info("Change password for user: {}", authentication.getName());

        profileService.changePassword(authentication.getName(), request);

        return ResponseEntity.ok(
                ApiResponse.success("Password changed successfully", null, HttpStatus.OK.value())
        );
    }
}