package com.example.coursemanagement.controllers;

import com.example.coursemanagement.models.Pagination;
import com.example.coursemanagement.models.dto.CourseDTO;
import com.example.coursemanagement.models.dto.LearnerDTO;
import com.example.coursemanagement.services.CourseService;
import com.example.coursemanagement.services.LearnerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/learners")
@RequiredArgsConstructor
@Tag(name = "Learner API", description = "Operations related to Learners")
public class LearnerController {

    private final LearnerService learnerService;
    private final CourseService courseService;

    // Get All Learners
    @GetMapping
    @Operation(
            summary = "Get All Learners",
            description = "Get All Learners In The System")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<Pagination<LearnerDTO>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Pagination<LearnerDTO> learners = learnerService.getAllLearners(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(learners);
    }

    // Lấy learner theo ID
    @GetMapping("/{id}")
    @Operation(summary = "Get learner by ID", description = "Retrieve a learner's details by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Learner found"),
            @ApiResponse(responseCode = "404", description = "Learner not found")
    })
    public ResponseEntity<LearnerDTO> getById(@PathVariable String id) {
        LearnerDTO learnerDTO = learnerService.getLearnerById(id);
        return ResponseEntity.status(HttpStatus.OK).body(learnerDTO);
    }

    // Lấy tất cả courses của learner
    @GetMapping("/{id}/courses")
    @Operation(summary = "Get courses by learner ID", description = "Retrieve all courses that a learner is enrolled in")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved courses")
    public ResponseEntity<List<CourseDTO>> getCourses(@PathVariable UUID id) {
        List<CourseDTO> courseDTOList = courseService.getCoursesByLearnerId(id);
        return ResponseEntity.status(HttpStatus.OK).body(courseDTOList);
    }

    // Tìm kiếm learners theo name
    @GetMapping("/search")
    @Operation(summary = "Search learners by name", description = "Search learners by partial or full name match")
    @ApiResponse(responseCode = "200", description = "Search completed successfully")
    public ResponseEntity<List<LearnerDTO>> search(@RequestParam String name) {
        List<LearnerDTO> learnerDTOs = learnerService.getLearnerByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(learnerDTOs);
    }

    // Tạo learner
    @PostMapping
    @Operation(summary = "Create a learner", description = "Add a new learner to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Learner created"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<LearnerDTO> create(@Valid @RequestBody LearnerDTO learnerDTO) {
        LearnerDTO created = learnerService.createLearner(learnerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Cập nhật learner
    @PutMapping("/{id}")
    @Operation(summary = "Update learner", description = "Update existing learner details by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Learner updated"),
            @ApiResponse(responseCode = "404", description = "Learner not found")
    })
    public ResponseEntity<LearnerDTO> update(@PathVariable String id, @RequestBody LearnerDTO learnerDTO) {
        LearnerDTO updated = learnerService.updateLearner(learnerDTO, id);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    // Xóa learner
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete learner", description = "Delete a learner by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Learner deleted"),
            @ApiResponse(responseCode = "409", description = "Learner has existing course enrollment")
    })
    public ResponseEntity<Void> delete(@PathVariable String id) {
        learnerService.deleteLearner(id);
        return ResponseEntity.noContent().build();
    }
}

