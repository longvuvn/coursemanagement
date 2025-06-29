package com.example.coursemanagement.models.dto;

import lombok.Data;

@Data
public class SubmissionDTO {
    private String id;
    private String file_Url;
    private String status;
    private String submissionAt;
    private String learnerName;
    private String lessonName;
    private String learnerId;
    private String lessonId;
}
