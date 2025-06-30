package com.example.coursemanagement.services;

import com.example.coursemanagement.models.dto.CourseDTO;

import java.util.List;

public interface CourseService {
    List<CourseDTO> getAllCourses();
    CourseDTO getCourseById(String id);
    CourseDTO createCourse(CourseDTO courseDTO);
    CourseDTO updateCourse(CourseDTO courseDTO, String id);
    CourseDTO updateTotalRating (String courseId);
    void deleteCourse(String id);
    List<CourseDTO> getCoursesByLearnerId(String learnerId);
    List<CourseDTO> getCoursesByTitle(String title);
    List<CourseDTO> getLatestCourses();
    List<CourseDTO> getOldestCourses();
}
