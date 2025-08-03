package com.example.coursemanagement.controllers;

import com.example.coursemanagement.models.dto.LessonDTO;
import com.example.coursemanagement.services.LessonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/lessons")
@RequiredArgsConstructor
@Tag(name = "Lesson Management", description = "Manage lessons in the course system")
public class LessonController {

    private final LessonService lessonService;

    @Operation(summary = "Get all lessons", description = "Returns a list of all lessons")
    @GetMapping
    public ResponseEntity<List<LessonDTO>> getAll() {
        List<LessonDTO> lessons = lessonService.getAllLessons();
        return ResponseEntity.status(HttpStatus.OK).body(lessons);
    }

    @Operation(summary = "Get lesson by ID", description = "Returns the details of a lesson by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<LessonDTO> getById(
            @Parameter(description = "ID of the lesson", example = "lesson-uuid-123")
            @PathVariable String id) {
        LessonDTO lesson = lessonService.getLessonById(id);
        return ResponseEntity.status(HttpStatus.OK).body(lesson);
    }

    @Operation(summary = "Search lessons by title", description = "Returns a list of lessons that match the given title")
    @GetMapping("/search")
    public ResponseEntity<List<LessonDTO>> getByTitle(
            @Parameter(description = "Title of the lesson to search for", example = "Introduction to Java")
            @RequestParam String title) {
        List<LessonDTO> lessons = lessonService.getLessonsByTitle(title);
        return ResponseEntity.status(HttpStatus.OK).body(lessons);
    }

    @Operation(summary = "Get latest lessons", description = "Returns the most recently created lessons")
    @GetMapping("/latest")
    public ResponseEntity<List<LessonDTO>> getLatest() {
        List<LessonDTO> lessons = lessonService.getLatestLessons();
        return ResponseEntity.status(HttpStatus.OK).body(lessons);
    }

    @Operation(summary = "Get oldest lessons", description = "Returns the earliest created lessons")
    @GetMapping("/oldest")
    public ResponseEntity<List<LessonDTO>> getOldest() {
        List<LessonDTO> lessons = lessonService.getOldestLessons();
        return ResponseEntity.status(HttpStatus.OK).body(lessons);
    }

    @Operation(summary = "Create a new lesson", description = "Adds a new lesson to the system")
    @PostMapping
    public ResponseEntity<LessonDTO> create(
            @Parameter(description = "Lesson data to be created")
            @RequestBody @Valid LessonDTO lessonDTO) {
        LessonDTO created = lessonService.createLesson(lessonDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Update a lesson", description = "Updates the information of an existing lesson by ID")
    @PutMapping("/{id}")
    public ResponseEntity<LessonDTO> update(
            @Parameter(description = "ID of the lesson to update", example = "lesson-uuid-123")
            @PathVariable String id,
            @Parameter(description = "Updated lesson data")
            @RequestBody @Valid LessonDTO lessonDTO) {
        LessonDTO updated = lessonService.updateLesson(lessonDTO, id);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @Operation(summary = "Delete a lesson", description = "Deletes a lesson from the system by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the lesson to delete", example = "lesson-uuid-123")
            @PathVariable String id) {
        lessonService.deleteLesson(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
