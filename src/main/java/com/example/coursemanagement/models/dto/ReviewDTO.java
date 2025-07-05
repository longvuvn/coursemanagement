package com.example.coursemanagement.models.dto;

import lombok.Data;

@Data
public class ReviewDTO {
    private String id;
    private String comment;
    private String rating;
    private String learnerId;
    private String courseId;
    private String learnerName;
    private String createdAt;
    private String updatedAt;
    private String status;
}
