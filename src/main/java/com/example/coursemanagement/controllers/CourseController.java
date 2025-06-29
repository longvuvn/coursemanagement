package com.example.coursemanagement.controllers;

import com.example.coursemanagement.models.APIResponse;
import com.example.coursemanagement.models.dto.CourseDTO;
import com.example.coursemanagement.services.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<APIResponse<List<CourseDTO>>> getAllCourses() {
        List<CourseDTO> courses = courseService.getAllCourses();
        APIResponse<List<CourseDTO>> response = new APIResponse<>(
                "success",
                "Courses retrieved successfully",
                courses,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<CourseDTO>> getCourseById(@PathVariable String id) {
        CourseDTO course = courseService.getCourseById(id);
        APIResponse<CourseDTO> response = new APIResponse<>(
                "success",
                "Course retrieved successfully",
                course,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<APIResponse<CourseDTO>> createCourse(@RequestBody CourseDTO courseDTO) {
        CourseDTO created = courseService.createCourse(courseDTO);
        APIResponse<CourseDTO> response = new APIResponse<>(
                "success",
                "Course created successfully",
                created,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<CourseDTO>> updateCourse(@PathVariable String id, @RequestBody CourseDTO courseDTO) {
        CourseDTO updated = courseService.updateCourse(courseDTO, id);
        APIResponse<CourseDTO> response = new APIResponse<>(
                "success",
                "Course updated successfully",
                updated,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> deleteCourse(@PathVariable String id) {
        courseService.deleteCourse(id);
        APIResponse<Void> response = new APIResponse<>(
                "success",
                "Course deleted successfully",
                null,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}

