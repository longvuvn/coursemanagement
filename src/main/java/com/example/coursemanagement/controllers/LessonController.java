package com.example.coursemanagement.controllers;

import com.example.coursemanagement.models.APIResponse;
import com.example.coursemanagement.models.dto.LessonDTO;
import com.example.coursemanagement.services.LessonService;
import com.example.coursemanagement.services.exceptions.errors.BadRequestException;
import com.example.coursemanagement.services.exceptions.errors.ResourceNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/lessons")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    // Lấy tất cả bài học
    @GetMapping
    public ResponseEntity<APIResponse<List<LessonDTO>>> getAll() {
        try {
            List<LessonDTO> lessons = lessonService.getAllLessons();
            return ResponseEntity.ok(new APIResponse<>(
                    "success",
                    "Lessons retrieved successfully",
                    lessons,
                    null,
                    LocalDateTime.now()
            ));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse<>(
                    "error",
                    "Failed to retrieve lessons",
                    null,
                    Map.of("error", ex.getMessage() + " Please try again later."),
                    LocalDateTime.now()
            ));
        }
    }

    // Lấy bài học theo ID
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<LessonDTO>> getById(@PathVariable String id) {
        try {
            LessonDTO lesson = lessonService.getLessonById(id);
            return ResponseEntity.ok(new APIResponse<>(
                    "success",
                    "Lesson retrieved successfully",
                    lesson,
                    null,
                    LocalDateTime.now()
            ));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse<>(
                    "error",
                    "Failed to retrieve lesson",
                    null,
                    Map.of("error", ex.getMessage()),
                    LocalDateTime.now()
            ));
        }
    }

    // Tìm bài học theo tiêu đề
    @GetMapping("/search")
    public ResponseEntity<APIResponse<List<LessonDTO>>> getByTitle(@RequestParam String title) {
        try {
            List<LessonDTO> lessons = lessonService.getLessonsByTitle(title);
            return ResponseEntity.ok(new APIResponse<>(
                    "success",
                    "Lessons retrieved successfully",
                    lessons,
                    null,
                    LocalDateTime.now()
            ));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse<>(
                    "error",
                    "Failed to retrieve lessons",
                    null,
                    Map.of("error", ex.getMessage()),
                    LocalDateTime.now()
            ));
        }
    }

    // Lấy bài học mới nhất
    @GetMapping("/latest")
    public ResponseEntity<APIResponse<List<LessonDTO>>> getLatest() {
        try {
            List<LessonDTO> lessons = lessonService.getLatestLessons();
            return ResponseEntity.ok(new APIResponse<>(
                    "success",
                    "Latest lessons retrieved successfully",
                    lessons,
                    null,
                    LocalDateTime.now()
            ));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse<>(
                    "error",
                    "Failed to retrieve latest lessons",
                    null,
                    Map.of("error", ex.getMessage()),
                    LocalDateTime.now()
            ));
        }
    }

    // Lấy bài học cũ nhất
    @GetMapping("/oldest")
    public ResponseEntity<APIResponse<List<LessonDTO>>> getOldest() {
        try {
            List<LessonDTO> lessons = lessonService.getOldestLessons();
            return ResponseEntity.ok(new APIResponse<>(
                    "success",
                    "Oldest lessons retrieved successfully",
                    lessons,
                    null,
                    LocalDateTime.now()
            ));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse<>(
                    "error",
                    "Failed to retrieve oldest lessons",
                    null,
                    Map.of("error", ex.getMessage()),
                    LocalDateTime.now()
            ));
        }
    }

    // Tạo bài học
    @PostMapping
    public ResponseEntity<APIResponse<LessonDTO>> create(@RequestBody @Valid LessonDTO lessonDTO) {
        try {
            LessonDTO created = lessonService.createLesson(lessonDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(new APIResponse<>(
                    "success",
                    "Lesson created successfully",
                    created,
                    null,
                    LocalDateTime.now()
            ));
        } catch (BadRequestException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new APIResponse<>(
                    "error",
                    "Failed to create lesson",
                    null,
                    Map.of("error", ex.getMessage()),
                    LocalDateTime.now()
            ));
        }
    }

    // Cập nhật bài học
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<LessonDTO>> update(@PathVariable String id, @RequestBody @Valid LessonDTO lessonDTO) {
        try {
            LessonDTO updated = lessonService.updateLesson(lessonDTO, id);
            return ResponseEntity.ok(new APIResponse<>(
                    "success",
                    "Lesson updated successfully",
                    updated,
                    null,
                    LocalDateTime.now()
            ));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse<>(
                    "error",
                    "Failed to update lesson",
                    null,
                    Map.of("error", ex.getMessage()),
                    LocalDateTime.now()
            ));
        }
    }

    // Xoá bài học
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> delete(@PathVariable String id) {
        try {
            lessonService.deleteLesson(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new APIResponse<>(
                    "success",
                    "Lesson deleted successfully",
                    null,
                    null,
                    LocalDateTime.now()
            ));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse<>(
                    "error",
                    "Failed to delete lesson",
                    null,
                    Map.of("error", ex.getMessage()),
                    LocalDateTime.now()
            ));
        }
    }
}


