package com.example.coursemanagement.models.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRegister {
    private String fullName;
    private String email;
    private String phoneNumber;
}
