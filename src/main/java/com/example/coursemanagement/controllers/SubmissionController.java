package com.example.coursemanagement.controllers;

import com.example.coursemanagement.models.dto.SubmissionDTO;
import com.example.coursemanagement.services.SubmissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/v1/submissions")
@RequiredArgsConstructor
public class SubmissionController {

    private final SubmissionService submissionService;

    // Lấy tất cả submissions
    @GetMapping
    public ResponseEntity<List<SubmissionDTO>> getAll() {
        List<SubmissionDTO> submissions = submissionService.getAllSubmissions();
        return ResponseEntity.status(HttpStatus.OK).body(submissions);
    }

    // Lấy submission theo id
    @GetMapping("/{id}")
    public ResponseEntity<SubmissionDTO> getById(@PathVariable String id) {
        SubmissionDTO submission = submissionService.getSubmissionById(id);
        return ResponseEntity.status(HttpStatus.OK).body(submission);
    }

    // Tạo submission
    @PostMapping
    public ResponseEntity<SubmissionDTO> create(@Valid @RequestBody SubmissionDTO submissionDTO) {
        SubmissionDTO created = submissionService.createSubmission(submissionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Cập nhật submission
    @PutMapping("/{id}")
    public ResponseEntity<SubmissionDTO> update(@PathVariable String id, @RequestBody SubmissionDTO submissionDTO) {
        SubmissionDTO updated = submissionService.updateSubmission(submissionDTO, id);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    // Xóa submission
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        submissionService.deleteSubmission(id);
        return ResponseEntity.noContent().build();
    }
}

