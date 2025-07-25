package com.example.coursemanagement.models.dto;

import lombok.Data;

@Data
public class AdminDTO {
    private String id;
    private String fullName;
    private String avatar;
    private String email;
    private String phoneNumber;
    private String password;
    private String status;
    private String roleName;
    private String createdAt;
    private String updatedAt;
    private String department;
}
