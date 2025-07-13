package com.example.coursemanagement.controllers;

import com.example.coursemanagement.models.APIResponse;
import com.example.coursemanagement.models.dto.SubmissionDTO;
import com.example.coursemanagement.services.SubmissionService;
import com.example.coursemanagement.services.exceptions.errors.BadRequestException;
import com.example.coursemanagement.services.exceptions.errors.ResourceNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/submissions")
@RequiredArgsConstructor
public class SubmissionController {

    private final SubmissionService submissionService;

    //lấy tất cả submissions
    @GetMapping
    public ResponseEntity<APIResponse<List<SubmissionDTO>>> getAll() {
        try{
            List<SubmissionDTO> submissions = submissionService.getAllSubmissions();
            APIResponse<List<SubmissionDTO>> response = new APIResponse<>(
                    "success",
                    "Submissions retrieved successfully",
                    submissions,
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.ok(response);
        }catch (ResourceNotFoundException ex){
            APIResponse<List<SubmissionDTO>> response = new APIResponse<>(
                    "error",
                    "Submissions retrieved failed",
                    null,
                    Map.of("error", ex.getMessage() + " Please try again later."),
                    LocalDateTime.now()
            );
            return ResponseEntity.ok(response);
        }
    }

    //lấy submission theo id
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<SubmissionDTO>> getById(@PathVariable String id) {
        try{
            SubmissionDTO submission = submissionService.getSubmissionById(id);
            APIResponse<SubmissionDTO> response = new APIResponse<>(
                    "success",
                    "Submission retrieved successfully",
                    submission,
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.ok(response);
        }catch (ResourceNotFoundException ex){
            APIResponse<SubmissionDTO> response = new APIResponse<>(
                    "error",
                    "Submission not found",
                    null,
                    Map.of("error", ex.getMessage() + " Please try again later."),
                    LocalDateTime.now()
            );
            return ResponseEntity.ok(response);
        }
    }

    //tạo submission
    @PostMapping
    public ResponseEntity<APIResponse<SubmissionDTO>> create(@Valid @RequestBody SubmissionDTO submissionDTO) {
        try{
            SubmissionDTO created = submissionService.createSubmission(submissionDTO);
            APIResponse<SubmissionDTO> response = new APIResponse<>(
                    "success",
                    "Submission created successfully",
                    created,
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (BadRequestException ex){
            APIResponse<SubmissionDTO> response = new APIResponse<>(
                    "error",
                    "Submission created failed",
                    null,
                    Map.of("error", ex.getMessage() + " Please try again later."),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    //xóa submission
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<SubmissionDTO>> update(@PathVariable String id, @RequestBody SubmissionDTO submissionDTO) {
        try{
            SubmissionDTO updated = submissionService.updateSubmission(submissionDTO, id);
            APIResponse<SubmissionDTO> response = new APIResponse<>(
                    "success",
                    "Submission updated successfully",
                    updated,
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.ok(response);
        }catch (ResourceNotFoundException ex){
            APIResponse<SubmissionDTO> response = new APIResponse<>(
                    "error",
                    "Submission updated failed",
                    null,
                    Map.of("error", ex.getMessage() + " Please try again later."),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> delete(@PathVariable String id) {
        try{
            submissionService.deleteSubmission(id);
            APIResponse<Void> response = new APIResponse<>(
                    "success",
                    "Submission deleted successfully",
                    null,
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }catch (ResourceNotFoundException ex){
            APIResponse<Void> response = new APIResponse<>(
                    "error",
                    "Submission deleted failed",
                    null,
                    Map.of("error", ex.getMessage() + " Please try again later."),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
