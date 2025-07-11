package com.example.coursemanagement.services;

import com.example.coursemanagement.enums.CourseStatus;
import com.example.coursemanagement.models.Pagination;
import com.example.coursemanagement.models.dto.CourseDTO;

import java.util.List;
import java.util.UUID;

public interface CourseService {
    Pagination<CourseDTO> getAllCourses (int page, int size);
    CourseDTO getCourseById(String id);
    CourseDTO createCourse(CourseDTO courseDTO);
    CourseDTO updateCourse(CourseDTO courseDTO, String id);
    CourseDTO updateTotalRating (String courseId);
    void deleteCourse(String id);
    List<CourseDTO> getCoursesByLearnerId(UUID learnerId);
    List<CourseDTO> getCoursesByTitle(String title);
    List<CourseDTO> getLatestCourses();
    List<CourseDTO> getOldestCourses();
    Pagination<CourseDTO> getCoursesByCategoryName(String categoryName, int page, int size);

}
