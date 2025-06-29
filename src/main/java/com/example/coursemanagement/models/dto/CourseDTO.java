package com.example.coursemanagement.models.dto;

import com.example.coursemanagement.models.Chapter;
import com.example.coursemanagement.models.Lesson;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Data
public class CourseDTO {
    private String id;
    private String title;
    private String description;
    private String categoryName;
    private String status;
    private String image;
    private String price;
    private int totalReviews;
    private double averageRating;
    private String createdAt;
    private String updatedAt;
    private List<ChapterDTO> chapters;
}
