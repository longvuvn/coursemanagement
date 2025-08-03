package com.example.coursemanagement.controllers;

import com.example.coursemanagement.models.Pagination;
import com.example.coursemanagement.models.dto.CourseDTO;
import com.example.coursemanagement.models.dto.LearnerDTO;
import com.example.coursemanagement.services.CourseService;
import com.example.coursemanagement.services.LearnerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
@Tag(name = "Course Management", description = "Operations related to managing courses")
public class CourseController {

    private final CourseService courseService;
    private final LearnerService learnerService;

    @Operation(summary = "Get all active courses", description = "Returns paginated list of all courses with active status")
    @GetMapping
    public ResponseEntity<Pagination<CourseDTO>> getAll(
            @Parameter(description = "Page number (0-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Page size", example = "5")
            @RequestParam(defaultValue = "5") int size
    ) {
        Pagination<CourseDTO> courses = courseService.getAllCourses(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(courses);
    }

    @Operation(summary = "Get courses by category name", description = "Returns courses filtered by category name")
    @GetMapping("/categoryName")
    public ResponseEntity<Pagination<CourseDTO>> getByCategoryName(
            @Parameter(description = "Category name", example = "Programming")
            @RequestParam String categoryName,

            @Parameter(description = "Page number (0-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Page size", example = "5")
            @RequestParam(defaultValue = "5") int size
    ) {
        Pagination<CourseDTO> courses = courseService.getCoursesByCategoryName(categoryName, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(courses);
    }

    @Operation(summary = "Get course by ID", description = "Returns a specific course by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getById(
            @Parameter(description = "Course ID", example = "abc123")
            @PathVariable String id
    ) {
        CourseDTO course = courseService.getCourseById(id);
        return ResponseEntity.status(HttpStatus.OK).body(course);
    }

    @Operation(summary = "Get learners by course ID", description = "Returns all learners enrolled in a course")
    @GetMapping("/{id}/learners")
    public ResponseEntity<List<LearnerDTO>> getLearnersByCourseId(
            @Parameter(description = "Course ID", example = "4e204e10-c843-4d75-8cd7-0ac9eb35d2b0")
            @PathVariable UUID id
    ) {
        List<LearnerDTO> learners = learnerService.getLearnersByCourseId(id);
        return ResponseEntity.status(HttpStatus.OK).body(learners);
    }

    @Operation(summary = "Search courses by title", description = "Search for courses matching the title keyword")
    @GetMapping("/search")
    public ResponseEntity<List<CourseDTO>> search(
            @Parameter(description = "Course title keyword", example = "Java")
            @RequestParam String title
    ) {
        List<CourseDTO> courseDTOList = courseService.getCoursesByTitle(title);
        return ResponseEntity.status(HttpStatus.OK).body(courseDTOList);
    }

    @Operation(summary = "Get latest courses", description = "Returns a list of the most recently added courses")
    @GetMapping("/latest")
    public ResponseEntity<List<CourseDTO>> getLatest() {
        List<CourseDTO> courses = courseService.getLatestCourses();
        return ResponseEntity.status(HttpStatus.OK).body(courses);
    }

    @Operation(summary = "Get oldest courses", description = "Returns a list of the oldest courses")
    @GetMapping("/oldest")
    public ResponseEntity<List<CourseDTO>> getOldest() {
        List<CourseDTO> courses = courseService.getOldestCourses();
        return ResponseEntity.status(HttpStatus.OK).body(courses);
    }

    @Operation(summary = "Create new course", description = "Creates a new course")
    @PostMapping
    public ResponseEntity<CourseDTO> create(
            @Parameter(description = "Course data to be created")
            @Valid @RequestBody CourseDTO courseDTO
    ) {
        CourseDTO created = courseService.createCourse(courseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Update course", description = "Updates an existing course by ID")
    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> update(
            @Parameter(description = "ID of the course to be updated", example = "abc123")
            @PathVariable String id,

            @Parameter(description = "Updated course data")
            @RequestBody CourseDTO courseDTO
    ) {
        CourseDTO updated = courseService.updateCourse(courseDTO, id);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @Operation(summary = "Delete course", description = "Deletes a course by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the course to be deleted", example = "abc123")
            @PathVariable String id
    ) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}
