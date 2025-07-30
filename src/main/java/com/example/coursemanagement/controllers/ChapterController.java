package com.example.coursemanagement.controllers;

import com.example.coursemanagement.models.dto.ChapterDTO;
import com.example.coursemanagement.services.ChapterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/v1/chapters")
@RequiredArgsConstructor
public class ChapterController {

    private final ChapterService chapterService;

    // Lấy tất cả chapter
    @GetMapping
    public ResponseEntity<List<ChapterDTO>> getAll() {
        List<ChapterDTO> getAll = chapterService.getAllChapters();
        return ResponseEntity.ok(getAll);
    }

    // Lấy chapter theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ChapterDTO> getById(@PathVariable String id) {
        ChapterDTO chapter = chapterService.getChapterById(id);
        return ResponseEntity.status(HttpStatus.OK).body(chapter);
    }

    // Lấy chapter theo title
    @GetMapping("/search")
    public ResponseEntity<List<ChapterDTO>> getByTitle(@RequestParam String title) {
        List<ChapterDTO> chapters = chapterService.getChapterByTitle(title);
        return ResponseEntity.status(HttpStatus.OK).body(chapters);
    }

    // Lấy chapter mới nhất
    @GetMapping("/latest")
    public ResponseEntity<List<ChapterDTO>> getLatest() {
        List<ChapterDTO> chapters = chapterService.getLatestChapters();
        return ResponseEntity.ok(chapters);
    }

    // Lấy chapter cũ nhất
    @GetMapping("/oldest")
    public ResponseEntity<List<ChapterDTO>> getOldest() {
        List<ChapterDTO> chapters = chapterService.getOldestChapters();
        return ResponseEntity.status(HttpStatus.OK).body(chapters);
    }

    // Tạo mới chapter
    @PostMapping
    public ResponseEntity<ChapterDTO> create(@Valid @RequestBody ChapterDTO chapterDTO) {
        ChapterDTO created = chapterService.createChapter(chapterDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Cập nhật chapter
    @PutMapping("/{id}")
    public ResponseEntity<ChapterDTO> update(@PathVariable String id, @RequestBody ChapterDTO chapterDTO) {
        ChapterDTO updated = chapterService.updateChapter(chapterDTO, id);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    // Xoá chapter
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        chapterService.deleteChapter(id);
        return ResponseEntity.noContent().build();
    }
}

