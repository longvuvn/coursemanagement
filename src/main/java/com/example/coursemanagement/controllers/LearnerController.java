package com.example.coursemanagement.controllers;

import com.example.coursemanagement.models.APIResponse;
import com.example.coursemanagement.models.dto.LearnerDTO;
import com.example.coursemanagement.services.LearnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/learners")
@RequiredArgsConstructor
public class LearnerController {

    private final LearnerService learnerService;

    @GetMapping()
    public ResponseEntity<APIResponse<List<LearnerDTO>>> getAllLearners() {
        List<LearnerDTO> learners = learnerService.getAllLearners();
        APIResponse<List<LearnerDTO>> response = new APIResponse<>(
                "success",
                "Learners retrieved successfully",
                learners,
                null,
                LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<LearnerDTO>> getLearnerById(@PathVariable String id) {
        LearnerDTO learnerDTO = learnerService.getLearnerById(id);
        APIResponse<LearnerDTO> response = new APIResponse<>(
                "success",
                "Learner retrieved successfully",
                learnerDTO,
                null,
                LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<APIResponse<LearnerDTO>> createLearner(@Valid @RequestBody LearnerDTO learnerDTO) {
        LearnerDTO created = learnerService.createLearner(learnerDTO);
        APIResponse<LearnerDTO> response = new APIResponse<>(
                "success",
                "Learner created successfully",
                created,
                null,
                LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<LearnerDTO>> updateLearner(@PathVariable String id, @RequestBody LearnerDTO learnerDTO) {
        LearnerDTO updated = learnerService.updateLearner(learnerDTO, id);
        APIResponse<LearnerDTO> response = new APIResponse<>(
                "success",
                "Learner updated successfully",
                updated,
                null,
                LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> deleteLearner(@PathVariable String id) {
        learnerService.deleteLearner(id);
        APIResponse<Void> response = new APIResponse<>(
                "success",
                "Learner deleted successfully",
                null,
                null,
                LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}
