package com.example.coursemanagement.controllers;

import com.example.coursemanagement.models.APIResponse;
import com.example.coursemanagement.models.dto.SubmissionDTO;
import com.example.coursemanagement.services.SubmissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/submissions")
@RequiredArgsConstructor
public class SubmissionController {

    private final SubmissionService submissionService;

    //lấy tất cả submissions
    @GetMapping
    public ResponseEntity<APIResponse<List<SubmissionDTO>>> getAllSubmissions() {
        List<SubmissionDTO> submissions = submissionService.getAllSubmissions();
        APIResponse<List<SubmissionDTO>> response = new APIResponse<>(
                "success",
                "Submissions retrieved successfully",
                submissions,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

    //lấy submission theo id
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<SubmissionDTO>> getSubmissionById(@PathVariable String id) {
        SubmissionDTO submission = submissionService.getSubmissionById(id);
        APIResponse<SubmissionDTO> response = new APIResponse<>(
                "success",
                "Submission retrieved successfully",
                submission,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

    //tạo submission
    @PostMapping
    public ResponseEntity<APIResponse<SubmissionDTO>> createSubmission(@Valid @RequestBody SubmissionDTO submissionDTO) {
        SubmissionDTO created = submissionService.createSubmission(submissionDTO);
        APIResponse<SubmissionDTO> response = new APIResponse<>(
                "success",
                "Submission created successfully",
                created,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //xóa submission
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<SubmissionDTO>> updateSubmission(@PathVariable String id, @RequestBody SubmissionDTO submissionDTO) {
        SubmissionDTO updated = submissionService.updateSubmission(submissionDTO, id);
        APIResponse<SubmissionDTO> response = new APIResponse<>(
                "success",
                "Submission updated successfully",
                updated,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> deleteSubmission(@PathVariable String id) {
        submissionService.deleteSubmission(id);
        APIResponse<Void> response = new APIResponse<>(
                "success",
                "Submission deleted successfully",
                null,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}
