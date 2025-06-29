package com.example.coursemanagement.controllers;


import com.example.coursemanagement.models.APIResponse;
import com.example.coursemanagement.models.dto.ChapterDTO;
import com.example.coursemanagement.services.ChapterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/chapters")
@RequiredArgsConstructor
public class ChapterController {

    private final ChapterService chapterService;

    @GetMapping
    public ResponseEntity<APIResponse<List<ChapterDTO>>> getAllChapters() {
        List<ChapterDTO> chapters = chapterService.getAllChapters();
        APIResponse<List<ChapterDTO>> response = new APIResponse<>(
                "success",
                "Chapters retrieved successfully",
                chapters,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<ChapterDTO>> getChapterById(@PathVariable String id) {
        ChapterDTO chapter = chapterService.getChapterById(id);
        APIResponse<ChapterDTO> response = new APIResponse<>(
                "success",
                "Chapter retrieved successfully",
                chapter,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<APIResponse<ChapterDTO>> createChapter(@RequestBody ChapterDTO chapterDTO) {
        ChapterDTO created = chapterService.createChapter(chapterDTO);
        APIResponse<ChapterDTO> response = new APIResponse<>(
                "success",
                "Chapter created successfully",
                created,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<ChapterDTO>> updateChapter(@PathVariable String id, @RequestBody ChapterDTO chapterDTO) {
        ChapterDTO updated = chapterService.updateChapter(chapterDTO, id);
        APIResponse<ChapterDTO> response = new APIResponse<>(
                "success",
                "Chapter updated successfully",
                updated,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> deleteChapter(@PathVariable String id) {
        chapterService.deleteChapter(id);
        APIResponse<Void> response = new APIResponse<>(
                "success",
                "Chapter deleted successfully",
                null,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}
