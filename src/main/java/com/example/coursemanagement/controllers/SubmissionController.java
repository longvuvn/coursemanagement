package com.example.coursemanagement.controllers;

import com.example.coursemanagement.models.dto.SubmissionDTO;
import com.example.coursemanagement.services.SubmissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/submissions")
@RequiredArgsConstructor
@Tag(name = "Submission Management", description = "Operations related to submissions")
public class SubmissionController {

    private final SubmissionService submissionService;

    @Operation(summary = "Get all submissions")
    @GetMapping
    public ResponseEntity<List<SubmissionDTO>> getAll() {
        List<SubmissionDTO> submissions = submissionService.getAllSubmissions();
        return ResponseEntity.status(HttpStatus.OK).body(submissions);
    }

    @Operation(summary = "Get submission by ID")
    @GetMapping("/{id}")
    public ResponseEntity<SubmissionDTO> getById(@PathVariable String id) {
        SubmissionDTO submission = submissionService.getSubmissionById(id);
        return ResponseEntity.status(HttpStatus.OK).body(submission);
    }

    @Operation(summary = "Create a new submission")
    @PostMapping
    public ResponseEntity<SubmissionDTO> create(@Valid @RequestBody SubmissionDTO submissionDTO) {
        SubmissionDTO created = submissionService.createSubmission(submissionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Update an existing submission")
    @PutMapping("/{id}")
    public ResponseEntity<SubmissionDTO> update(@PathVariable String id, @RequestBody SubmissionDTO submissionDTO) {
        SubmissionDTO updated = submissionService.updateSubmission(submissionDTO, id);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @Operation(summary = "Delete a submission by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        submissionService.deleteSubmission(id);
        return ResponseEntity.noContent().build();
    }
}
