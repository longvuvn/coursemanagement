package com.example.coursemanagement.controllers;

import com.example.coursemanagement.models.APIResponse;
import com.example.coursemanagement.models.dto.ChapterDTO;
import com.example.coursemanagement.services.ChapterService;
import com.example.coursemanagement.services.exceptions.errors.BadRequestException;
import com.example.coursemanagement.services.exceptions.errors.ResourceNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/chapters")
@RequiredArgsConstructor
public class ChapterController {

    private final ChapterService chapterService;

    // lấy tất cả chapter
    @GetMapping
    public ResponseEntity<APIResponse<List<ChapterDTO>>> getAll() {
        try{
            List<ChapterDTO> chapters = chapterService.getAllChapters();
            APIResponse<List<ChapterDTO>> response = new APIResponse<>(
                    "success",
                    "Chapters retrieved successfully",
                    chapters,
                    null,
                    LocalDateTime.now());
            return ResponseEntity.ok(response);
        }catch(ResourceNotFoundException ex){
            APIResponse<List<ChapterDTO>> response = new APIResponse<>(
                    "error",
                    "Chapters retrieved failed",
                    null,
                    Map.of("error", ex.getMessage()),
                    LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // lấy chapter theo id
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<ChapterDTO>> getById(@PathVariable String id) {
        try{
            ChapterDTO chapter = chapterService.getChapterById(id);
            APIResponse<ChapterDTO> response = new APIResponse<>(
                    "success",
                    "Chapter retrieved successfully",
                    chapter,
                    null,
                    LocalDateTime.now());
            return ResponseEntity.ok(response);
        }catch (ResourceNotFoundException ex){
            APIResponse<ChapterDTO> response = new APIResponse<>(
                    "success",
                    "Chapter not found",
                    null,
                    Map.of("error", ex.getMessage()),
                    LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // lấy chapter theo title
    @GetMapping("/search")
    public ResponseEntity<APIResponse<List<ChapterDTO>>> getByTitle(@RequestParam String title) {
        try{
            List<ChapterDTO> chapters = chapterService.getChapterByTitle(title);
            APIResponse<List<ChapterDTO>> response = new APIResponse<>(
                    "success",
                    "Chapters retrieved successfully",
                    chapters,
                    null,
                    LocalDateTime.now());
            return ResponseEntity.ok(response);
        }catch (ResourceNotFoundException ex){
            APIResponse<List<ChapterDTO>> response = new APIResponse<>(
                    "error",
                    "Chapters not found with title: " + title,
                    null,
                    Map.of("error", ex.getMessage()),
                    LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // lấy chapter mới nhất
    @GetMapping("/latest")
    public ResponseEntity<APIResponse<List<ChapterDTO>>> getLatest() {
        try{
            List<ChapterDTO> chapters = chapterService.getLatestChapters();
            APIResponse<List<ChapterDTO>> response = new APIResponse<>(
                    "success",
                    "Chapters retrieved successfully",
                    chapters,
                    null,
                    LocalDateTime.now());
            return ResponseEntity.ok(response);
        }catch (ResourceNotFoundException ex){
            APIResponse<List<ChapterDTO>> response = new APIResponse<>(
                    "error",
                    "Chapters retrieved failed",
                    null,
                    Map.of("error", ex.getMessage()),
                    LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // lấy chapter cũ nhất
    @GetMapping("/oldest")
    public ResponseEntity<APIResponse<List<ChapterDTO>>> getOldest() {
        try{
            List<ChapterDTO> chapters = chapterService.getOldestChapters();
            APIResponse<List<ChapterDTO>> response = new APIResponse<>(
                    "success",
                    "Chapters retrieved successfully",
                    chapters,
                    null,
                    LocalDateTime.now());
            return ResponseEntity.ok(response);
        }catch (ResourceNotFoundException ex){
            APIResponse<List<ChapterDTO>> response = new APIResponse<>(
                    "error",
                    "Chapters retrieved failed",
                    null,
                    Map.of("error", ex.getMessage()),
                    LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // Tạo chapter
    @PostMapping
    public ResponseEntity<APIResponse<ChapterDTO>> create(@Valid @RequestBody ChapterDTO chapterDTO) {
        try{
            ChapterDTO created = chapterService.createChapter(chapterDTO);
            APIResponse<ChapterDTO> response = new APIResponse<>(
                    "success",
                    "Chapter created successfully",
                    created,
                    null,
                    LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (BadRequestException ex){
            APIResponse<ChapterDTO> response = new APIResponse<>(
                    "error",
                    "Chapter created failed",
                    null,
                    Map.of("error", ex.getMessage()),
                    LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // Chỉnh sửa chapter
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<ChapterDTO>> update(@PathVariable String id, @RequestBody ChapterDTO chapterDTO) {
        try{
            ChapterDTO updated = chapterService.updateChapter(chapterDTO, id);
            APIResponse<ChapterDTO> response = new APIResponse<>(
                    "success",
                    "Chapter updated successfully",
                    updated,
                    null,
                    LocalDateTime.now());
            return ResponseEntity.ok(response);
        }catch (ResourceNotFoundException ex){
            APIResponse<ChapterDTO> response = new APIResponse<>(
                    "error",
                    "Chapter updated failed",
                    null,
                    Map.of("error", ex.getMessage()),
                    LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // xóa chapter
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> delete(@PathVariable String id) {
        try{
            chapterService.deleteChapter(id);
            APIResponse<Void> response = new APIResponse<>(
                    "success",
                    "Chapter deleted successfully",
                    null,
                    null,
                    LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }catch (ResourceNotFoundException ex){
            APIResponse<Void> response = new APIResponse<>(
                    "error",
                    "Chapter deleted failed",
                    null,
                    Map.of("error", ex.getMessage()),
                    LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
