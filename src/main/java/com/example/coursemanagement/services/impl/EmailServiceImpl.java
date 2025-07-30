package com.example.coursemanagement.services.impl;

import com.example.coursemanagement.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Override
    public void sendEmail(String toEmail, String fullName, String rawPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Your Account Has Been Created");
        message.setText("Hi " + fullName + ",\n\n"
                + "Your account has been successfully created.\n"
                + "Your temporary password is: " + rawPassword + "\n"
                + "Please log in and change your password.\n\n"
                + "Thank you!");

        javaMailSender.send(message);
    }

    @Override
    public String generateRandomPassword() {
        return UUID.randomUUID().toString().substring(0, 8) + "Ab";
    }
}
