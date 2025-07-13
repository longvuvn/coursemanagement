package com.example.coursemanagement.controllers;

import com.example.coursemanagement.models.APIResponse;
import com.example.coursemanagement.models.Pagination;
import com.example.coursemanagement.models.dto.CourseDTO;
import com.example.coursemanagement.models.dto.LearnerDTO;
import com.example.coursemanagement.services.CourseService;
import com.example.coursemanagement.services.LearnerService;
import com.example.coursemanagement.services.exceptions.errors.ResourceNotFoundException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/learners")
@RequiredArgsConstructor
public class LearnerController {

    private final LearnerService learnerService;
    private final CourseService courseService;

    //Get All Learners
    @GetMapping()
    public ResponseEntity<APIResponse<Pagination<LearnerDTO>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        try{
            Pagination<LearnerDTO> learners = learnerService.getAllLearners(page, size);
            APIResponse<Pagination<LearnerDTO>> response = new APIResponse<>(
                    "success",
                    "Learners retrieved successfully",
                    learners,
                    null,
                    LocalDateTime.now()
            );

            return ResponseEntity.ok(response);
        }catch (ResourceNotFoundException ex){
            APIResponse<Pagination<LearnerDTO>> response = new APIResponse<>(
                    "error",
                    "Learners retrieved failed",
                    null,
                    Map.of("error", ex.getMessage()),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    //lấy learners theo Id
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<LearnerDTO>> getById(@PathVariable String id) {
        try {
            LearnerDTO learnerDTO = learnerService.getLearnerById(id);
            APIResponse<LearnerDTO> response = new APIResponse<>(
                    "success",
                    "Learner retrieved successfully",
                    learnerDTO,
                    null,
                    LocalDateTime.now());
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException ex) {
            APIResponse<LearnerDTO> response = new APIResponse<>(
                    "error",
                    "Learner retrieved failed",
                    null,
                    Map.of("error", ex.getMessage()),
                    LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    //lấy tất cả courses của learners
    @GetMapping("/{id}/courses")
    public ResponseEntity<APIResponse<List<CourseDTO>>> getCourses(@PathVariable UUID id) {
        try {
            List<CourseDTO> courseDTOList = courseService.getCoursesByLearnerId(id);
            APIResponse<List<CourseDTO>> response = new APIResponse<>(
                    "success",
                    "Learner's courses retrieved successfully",
                    courseDTOList,
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException ex) {
            APIResponse<List<CourseDTO>> response = new APIResponse<>(
                    "error",
                    "Learner's courses retrieval failed",
                    null,
                    Map.of("error", ex.getMessage()),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    //tìm kiếm learners theo name
    @GetMapping("/search")
    public ResponseEntity<APIResponse<List<LearnerDTO>>> search(@RequestParam String name) {
        try {
            List<LearnerDTO> learnerDTOs = learnerService.getLearnerByName(name);
            APIResponse<List<LearnerDTO>> response = new APIResponse<>(
                    "success",
                    "Learners retrieved successfully",
                    learnerDTOs,
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException ex) {
            APIResponse<List<LearnerDTO>> response = new APIResponse<>(
                    "error",
                    "Learners search failed",
                    null,
                    Map.of("error", ex.getMessage()),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    //tạo learner
    @PostMapping()
    public ResponseEntity<APIResponse<LearnerDTO>> create(@Valid @RequestBody LearnerDTO learnerDTO) {
        try {
            LearnerDTO created = learnerService.createLearner(learnerDTO);
            APIResponse<LearnerDTO> response = new APIResponse<>(
                    "success",
                    "Learner created successfully",
                    created,
                    null,
                    LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (ConstraintViolationException ex){
            APIResponse<LearnerDTO> response = new APIResponse<>(
                    "error",
                    "Learners search failed",
                    null,
                    Map.of("error", ex.getMessage()),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    //chỉnh sửa learner
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<LearnerDTO>> update(@PathVariable String id, @RequestBody LearnerDTO learnerDTO) {
        try{
            LearnerDTO updated = learnerService.updateLearner(learnerDTO, id);
            APIResponse<LearnerDTO> response = new APIResponse<>(
                    "success",
                    "Learner updated successfully",
                    updated,
                    null,
                    LocalDateTime.now());
            return ResponseEntity.ok(response);
        }catch (ResourceNotFoundException ex){
            APIResponse<LearnerDTO> response = new APIResponse<>(
                    "error",
                    "Learner updated failed",
                    null,
                    Map.of("error", ex.getMessage()),
                    LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    //xóa learner
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> delete(@PathVariable String id) {
        try{
            learnerService.deleteLearner(id);
            APIResponse<Void> response = new APIResponse<>(
                    "success",
                    "Learner deleted successfully",
                    null,
                    null,
                    LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }catch (ResourceNotFoundException ex){
            APIResponse<Void> response = new APIResponse<>(
                    "error",
                    "Learner deleted failed",
                    null,
                    Map.of("error", ex.getMessage()),
                    LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
