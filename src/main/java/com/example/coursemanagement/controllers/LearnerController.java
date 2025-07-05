package com.example.coursemanagement.controllers;

import com.example.coursemanagement.models.APIResponse;
import com.example.coursemanagement.models.dto.CourseDTO;
import com.example.coursemanagement.models.dto.LearnerDTO;
import com.example.coursemanagement.services.CourseService;
import com.example.coursemanagement.services.LearnerService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/learners")
@RequiredArgsConstructor
public class LearnerController {

    private final LearnerService learnerService;
    private final CourseService courseService;

    //lấy tất cả learners
    @GetMapping()
    @PreAuthorize("hasRole('Admin')")
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

    //lấy learners theo Id
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
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

    //lấy tất cả courses của learners
    @GetMapping("/{id}/courses")
    public ResponseEntity<APIResponse<List<CourseDTO>>> getLearnersCourses(@PathVariable UUID id) {
        List<CourseDTO> courseDTOList = courseService.getCoursesByLearnerId(id);
        APIResponse<List<CourseDTO>> response = new APIResponse<>(
                "success",
                "Learners courses retrieved successfully",
                courseDTOList,
                null,
                LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    //tìm kiếm learners theo name
    @GetMapping("/search")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<APIResponse<List<LearnerDTO>>> searchLearner (@RequestParam String name){
        List<LearnerDTO> learnerDTO = learnerService.getLearnerByName(name);
        APIResponse<List<LearnerDTO>> response = new APIResponse<>(
                "success",
                "Learner retrieved successfully",
                learnerDTO,
                null,
                LocalDateTime.now());
        return ResponseEntity.status(200).body(response);
    }

    //tạo learner
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

    //chỉnh sửa learner
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
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

    //xóa learner
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
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
