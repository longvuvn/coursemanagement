package com.example.coursemanagement.controllers;

import com.example.coursemanagement.models.dto.ChapterDTO;
import com.example.coursemanagement.services.ChapterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/chapters")
@RequiredArgsConstructor
@Tag(name = "Chapter Management", description = "Manage course chapters")
public class ChapterController {

    private final ChapterService chapterService;

    @Operation(summary = "Get all chapters")
    @ApiResponse(responseCode = "200", description = "List of chapters",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ChapterDTO.class)))
    @GetMapping
    public ResponseEntity<List<ChapterDTO>> getAll() {
        return ResponseEntity.ok(chapterService.getAllChapters());
    }

    @Operation(summary = "Get chapter by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Chapter found"),
            @ApiResponse(responseCode = "404", description = "Chapter not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ChapterDTO> getById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(chapterService.getChapterById(id));
    }

    @Operation(summary = "Search chapters by title")
    @GetMapping("/search")
    public ResponseEntity<List<ChapterDTO>> getByTitle(@RequestParam String title) {
        return ResponseEntity.status(HttpStatus.OK).body(chapterService.getChapterByTitle(title));
    }

    @Operation(summary = "Get the latest chapters")
    @GetMapping("/latest")
    public ResponseEntity<List<ChapterDTO>> getLatest() {
        return ResponseEntity.ok(chapterService.getLatestChapters());
    }

    @Operation(summary = "Get the oldest chapters")
    @GetMapping("/oldest")
    public ResponseEntity<List<ChapterDTO>> getOldest() {
        return ResponseEntity.status(HttpStatus.OK).body(chapterService.getOldestChapters());
    }

    @Operation(summary = "Create a new chapter")
    @ApiResponse(responseCode = "201", description = "Chapter created",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ChapterDTO.class)))
    @PostMapping
    public ResponseEntity<ChapterDTO> create(@Valid @RequestBody ChapterDTO chapterDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(chapterService.createChapter(chapterDTO));
    }

    @Operation(summary = "Update an existing chapter")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Chapter updated"),
            @ApiResponse(responseCode = "404", description = "Chapter not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ChapterDTO> update(@PathVariable String id, @RequestBody ChapterDTO chapterDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(chapterService.updateChapter(chapterDTO, id));
    }

    @Operation(summary = "Delete a chapter")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Chapter deleted"),
            @ApiResponse(responseCode = "404", description = "Chapter not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        chapterService.deleteChapter(id);
        return ResponseEntity.noContent().build();
    }
}
