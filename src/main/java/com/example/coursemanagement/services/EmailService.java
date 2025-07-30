package com.example.coursemanagement.services;

public interface EmailService {
    void sendEmail(String toEmail, String fullName, String rawPassword);
    String generateRandomPassword();
}
