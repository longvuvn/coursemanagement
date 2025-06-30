package com.example.coursemanagement.services;

import com.example.coursemanagement.models.auth.AuthRequest;
import com.example.coursemanagement.models.auth.AuthResponse;
import com.example.coursemanagement.models.auth.RefreshTokenRequest;

public interface AuthService {
    AuthResponse authenticate(AuthRequest authRequest);
    AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
