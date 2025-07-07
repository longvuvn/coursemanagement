package com.example.coursemanagement.models.dto;

import lombok.Data;

@Data
public class RegistrationDTO {
    private String id;
    private String learnerId;
    private String courseId;
    private String learnerName;
    private String courseTitle;
    private String status;
    private String registrationAt;
}
