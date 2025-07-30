package com.example.coursemanagement.controllers;

import com.example.coursemanagement.models.dto.LessonDTO;
import com.example.coursemanagement.services.LessonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/v1/lessons")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    @GetMapping
    public ResponseEntity<List<LessonDTO>> getAll() {
        List<LessonDTO> lessons = lessonService.getAllLessons();
        return ResponseEntity.status(HttpStatus.OK).body(lessons);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LessonDTO> getById(@PathVariable String id) {
        LessonDTO lesson = lessonService.getLessonById(id);
        return ResponseEntity.status(HttpStatus.OK).body(lesson);
    }

    @GetMapping("/search")
    public ResponseEntity<List<LessonDTO>> getByTitle(@RequestParam String title) {
        List<LessonDTO> lessons = lessonService.getLessonsByTitle(title);
        return ResponseEntity.status(HttpStatus.OK).body(lessons);
    }

    @GetMapping("/latest")
    public ResponseEntity<List<LessonDTO>> getLatest() {
        List<LessonDTO> lessons = lessonService.getLatestLessons();
        return ResponseEntity.status(HttpStatus.OK).body(lessons);
    }

    @GetMapping("/oldest")
    public ResponseEntity<List<LessonDTO>> getOldest() {
        List<LessonDTO> lessons = lessonService.getOldestLessons();
        return ResponseEntity.status(HttpStatus.OK).body(lessons);
    }

    @PostMapping
    public ResponseEntity<LessonDTO> create(@RequestBody @Valid LessonDTO lessonDTO) {
        LessonDTO created = lessonService.createLesson(lessonDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LessonDTO> update(@PathVariable String id, @RequestBody @Valid LessonDTO lessonDTO) {
        LessonDTO updated = lessonService.updateLesson(lessonDTO, id);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        lessonService.deleteLesson(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}




