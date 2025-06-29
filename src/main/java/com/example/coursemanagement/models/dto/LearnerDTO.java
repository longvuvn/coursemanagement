package com.example.coursemanagement.models.dto;

import lombok.Data;

@Data
public class LearnerDTO {
    private String id;
    private String fullName;
    private String avatar;
    private String email;
    private String phoneNumber;
    private String password;
    private String status;
    private String roleName;
    private String createdAt;
    private int totalCourses;
    private String updatedAt;
    private String level;
}
