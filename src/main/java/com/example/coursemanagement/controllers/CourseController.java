package com.example.coursemanagement.controllers;

import com.example.coursemanagement.models.APIResponse;
import com.example.coursemanagement.models.Pagination;
import com.example.coursemanagement.models.dto.CourseDTO;
import com.example.coursemanagement.models.dto.LearnerDTO;
import com.example.coursemanagement.services.CourseService;
import com.example.coursemanagement.services.LearnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final LearnerService learnerService;

    //lấy tất cả courses
    @GetMapping
    public ResponseEntity<APIResponse<Pagination<CourseDTO>>> getAllCourses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Pagination<CourseDTO> courses = courseService.getAllCourses(page, size);
        APIResponse<Pagination<CourseDTO>> response = new APIResponse<>(
                "success",
                "Courses retrieved successfully",
                courses,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

    //lấy course theo Id
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

    //lấy tất cả learners trong course
    @GetMapping("/{id}/learners")
    public ResponseEntity<APIResponse<List<LearnerDTO>>> getLearnersByCourseId(@PathVariable UUID id) {
        List<LearnerDTO> learners = learnerService.getLearnersByCourseId(id);
        APIResponse<List<LearnerDTO>> response = new APIResponse<>(
                "success",
                "Learners retrieved successfully",
                learners,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

    //tìm kiếm course theo title
    @GetMapping("/search")
    public ResponseEntity<APIResponse<List<CourseDTO>>> searchCourse (@RequestParam String title){
        List<CourseDTO> courseDTOList = courseService.getCoursesByTitle(title);
        APIResponse<List<CourseDTO>> response = new APIResponse<>(
                "success",
                "Courses retrieved successfully",
                courseDTOList,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.status(200).body(response);
    }

    //lấy course mới nhất
    @GetMapping("/latest")
    public ResponseEntity<APIResponse<List<CourseDTO>>> getLatestCourses() {
        List<CourseDTO> courses = courseService.getLatestCourses();
        APIResponse<List<CourseDTO>> response = new APIResponse<>(
                "success",
                "Courses retrieved successfully",
                courses,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

    //lấy khóa học cũ nhất
    @GetMapping("/oldest")
    public ResponseEntity<APIResponse<List<CourseDTO>>> getOldestCourses() {
        List<CourseDTO> courses = courseService.getOldestCourses();
        APIResponse<List<CourseDTO>> response = new APIResponse<>(
                "success",
                "Courses retrieved successfully",
                courses,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

    //tạo course
    @PostMapping
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<APIResponse<CourseDTO>> createCourse(@Valid @RequestBody CourseDTO courseDTO) {
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

    //chỉnh sửa course
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

    //xóa course
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

