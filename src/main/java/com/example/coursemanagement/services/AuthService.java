package com.example.coursemanagement.services;

import com.example.coursemanagement.models.auth.*;

public interface AuthService {
    AuthResponse authenticate(AuthRequest authRequest);
    AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
    ChangePasswordResponse ChangePassword(String username, ChangePasswordRequest changePasswordRequest);
}
