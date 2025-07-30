package com.example.coursemanagement.models.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private String userId;
    private String fullName;
    private String email;
    private String avatar;
}
