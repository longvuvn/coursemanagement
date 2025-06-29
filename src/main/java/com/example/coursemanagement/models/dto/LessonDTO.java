package com.example.coursemanagement.models.dto;

import lombok.Data;

import java.util.List;

@Data
public class LessonDTO {
    private String id;
    private String title;
    private String referenceLink;
    private String chapterId;
    private String content;
    private String status;
    private String createdAt;
    private String updatedAt;
    private List<SubmissionDTO> submissions;
}
