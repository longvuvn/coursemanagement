package com.example.coursemanagement.controllers;

import com.example.coursemanagement.models.auth.*;
import com.example.coursemanagement.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication", description = "Handles authentication, token refresh, and password change")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Login", description = "Authenticate user and return access token + refresh token")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> getToken(
            @RequestBody
            @Parameter(description = "Login information (email, password)") AuthRequest authRequest) {

        AuthResponse authResponse = authService.authenticate(authRequest);
        return ResponseEntity.ok(authResponse);
    }

    @Operation(summary = "Refresh access token", description = "Use refresh token to get a new access token")
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> getRefreshToken(
            @RequestBody
            @Parameter(description = "Issued refresh token") RefreshTokenRequest refreshTokenRequest) {

        AuthResponse authResponse = authService.refreshToken(refreshTokenRequest);
        return ResponseEntity.ok(authResponse);
    }

    @Operation(summary = "Change password", description = "Change the user's current password")
    @PostMapping("/change-password")
    public ResponseEntity<ChangePasswordResponse> changePassword(
            @Parameter(description = "Username (email or login name)") @RequestParam String username,
            @RequestBody
            @Parameter(description = "Current and new password") ChangePasswordRequest request) {

        ChangePasswordResponse response = authService.ChangePassword(username, request);
        return ResponseEntity.ok(response);
    }
}
