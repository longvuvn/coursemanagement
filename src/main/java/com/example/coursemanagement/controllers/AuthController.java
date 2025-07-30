package com.example.coursemanagement.controllers;


import com.example.coursemanagement.models.auth.*;
import com.example.coursemanagement.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> getToken(@RequestBody AuthRequest authRequest) {
        AuthResponse authResponse = authService.authenticate(authRequest);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> getRefreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        AuthResponse authResponse = authService.refreshToken(refreshTokenRequest);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/change-password")
    public ResponseEntity<ChangePasswordResponse> changePassword (@RequestParam String username, @RequestBody ChangePasswordRequest request){
        ChangePasswordResponse response = authService.ChangePassword(username, request);
        return ResponseEntity.ok(response);
    }
}
