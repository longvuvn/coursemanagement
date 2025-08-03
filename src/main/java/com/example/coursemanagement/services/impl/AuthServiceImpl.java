package com.example.coursemanagement.services.impl;

import com.example.coursemanagement.config.CustomUserDetailsService;
import com.example.coursemanagement.models.Learner;
import com.example.coursemanagement.models.User;
import com.example.coursemanagement.models.auth.*;
import com.example.coursemanagement.repositories.LearnerRepository;
import com.example.coursemanagement.services.AuthService;
import com.example.coursemanagement.services.exceptions.errors.ResourceNotFoundException;
import com.example.coursemanagement.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final LearnerRepository learnerRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse authenticate(AuthRequest authRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword())
        );
        User user = customUserDetailsService.getUserByUsername(authRequest.getUsername());
        String accessToken = jwtUtil.generateAccessToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);
        return new AuthResponse(accessToken, refreshToken);
    }


    @Override
    public AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();
        if (!jwtUtil.validateToken(refreshToken)) {
            return new AuthResponse("invalid", "invalid");
        }
        String email = jwtUtil.extractUsername(refreshToken);
        User user = customUserDetailsService.getUserByUsername(email);
        String newAccessToken = jwtUtil.generateAccessToken(user);

        return new AuthResponse(newAccessToken, refreshToken);
    }


    @Override
    public ChangePasswordResponse ChangePassword(String username, ChangePasswordRequest changePasswordRequest) {
        Learner learner = learnerRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

        if(!passwordEncoder.matches(changePasswordRequest.getOldPassword(), learner.getPassword())){
            throw new ResourceNotFoundException("Old Password Not Match");
        }

        learner.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        learnerRepository.save(learner);

        return new ChangePasswordResponse("Password Changed Successfully");
    }
}
