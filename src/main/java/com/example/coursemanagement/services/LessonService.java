package com.example.coursemanagement.services;

import com.example.coursemanagement.models.dto.CourseDTO;
import com.example.coursemanagement.models.dto.LessonDTO;

import java.util.List;

public interface LessonService {
    List<LessonDTO> getAllLessons();
    LessonDTO getLessonById(String id);
    LessonDTO createLesson(LessonDTO lessonDTO);
    LessonDTO updateLesson(LessonDTO lessonDTO, String id);
    void deleteLesson(String id);
    List<LessonDTO> getLessonsByChapterId(String chapterId);
    List<LessonDTO> getLessonsByTitle(String title);
    List<LessonDTO> getLatestLessons();
    List<LessonDTO> getOldestLessons();
}
