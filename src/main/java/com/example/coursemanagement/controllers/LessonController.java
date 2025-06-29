package com.example.coursemanagement.controllers;

import com.example.coursemanagement.models.APIResponse;
import com.example.coursemanagement.models.dto.LessonDTO;
import com.example.coursemanagement.services.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/lessons")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    @GetMapping
    public ResponseEntity<APIResponse<List<LessonDTO>>> getAllLessons() {
        List<LessonDTO> lessons = lessonService.getAllLessons();
        APIResponse<List<LessonDTO>> response = new APIResponse<>(
                "success",
                "Lessons retrieved successfully",
                lessons,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<LessonDTO>> getLessonById(@PathVariable String id) {
        LessonDTO lesson = lessonService.getLessonById(id);
        APIResponse<LessonDTO> response = new APIResponse<>(
                "success",
                "Lesson retrieved successfully",
                lesson,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<APIResponse<LessonDTO>> createLesson(@RequestBody LessonDTO lessonDTO) {
        LessonDTO created = lessonService.createLesson(lessonDTO);
        APIResponse<LessonDTO> response = new APIResponse<>(
                "success",
                "Lesson created successfully",
                created,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<LessonDTO>> updateLesson(@PathVariable String id, @RequestBody LessonDTO lessonDTO) {
        LessonDTO updated = lessonService.updateLesson(lessonDTO, id);
        APIResponse<LessonDTO> response = new APIResponse<>(
                "success",
                "Lesson updated successfully",
                updated,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> deleteLesson(@PathVariable String id) {
        lessonService.deleteLesson(id);
        APIResponse<Void> response = new APIResponse<>(
                "success",
                "Lesson deleted successfully",
                null,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}
