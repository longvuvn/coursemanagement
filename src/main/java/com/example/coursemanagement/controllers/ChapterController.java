package com.example.coursemanagement.controllers;


import com.example.coursemanagement.models.APIResponse;
import com.example.coursemanagement.models.dto.ChapterDTO;
import com.example.coursemanagement.models.dto.CourseDTO;
import com.example.coursemanagement.services.ChapterService;
import jakarta.validation.Valid;
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

    //lấy tất cả chapter
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

    //lấy chapter theo id
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

    //lấy chapter theo title
    @GetMapping("/search")
    public ResponseEntity<APIResponse<List<ChapterDTO>>> getChapterByTitle(@RequestParam String title) {
        List<ChapterDTO> chapters = chapterService.getChapterByTitle(title);
        APIResponse<List<ChapterDTO>> response = new APIResponse<>(
                "success",
                "Chapters retrieved successfully",
                chapters,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

    //lấy chapter mới nhất
    @GetMapping("/latest")
    public ResponseEntity<APIResponse<List<ChapterDTO>>> getLatestChapters() {
        List<ChapterDTO> chapters = chapterService.getLatestChapters();
        APIResponse<List<ChapterDTO>> response = new APIResponse<>(
                "success",
                "Chapters retrieved successfully",
                chapters,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

    //lấy chapter cũ nhất
    @GetMapping("/oldest")
    public ResponseEntity<APIResponse<List<ChapterDTO>>> getOldestChapters() {
        List<ChapterDTO> chapters = chapterService.getOldestChapters();
        APIResponse<List<ChapterDTO>> response = new APIResponse<>(
                "success",
                "Chapters retrieved successfully",
                chapters,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

    //Tạo chapter
    @PostMapping
    public ResponseEntity<APIResponse<ChapterDTO>> createChapter(@Valid @RequestBody ChapterDTO chapterDTO) {
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

    //Chỉnh sửa chapter
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

    //xóa chapter
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
