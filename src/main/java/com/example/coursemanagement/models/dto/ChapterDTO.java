package com.example.coursemanagement.models.dto;

import lombok.Data;

import java.util.List;

@Data
public class ChapterDTO {
    private String id;
    private String title;
    private String status;
    private String courseName;
    private String courseId;
    private String createdAt;
    private String updatedAt;
    private List<LessonDTO> lessons;
}
