package com.example.coursemanagement.controllers;

import com.example.coursemanagement.models.Pagination;
import com.example.coursemanagement.models.dto.CourseDTO;
import com.example.coursemanagement.models.dto.LearnerDTO;
import com.example.coursemanagement.services.CourseService;
import com.example.coursemanagement.services.LearnerService;
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
public class LearnerController {

    private final LearnerService learnerService;
    private final CourseService courseService;

    // Get All Learners
    @GetMapping
    public ResponseEntity<Pagination<LearnerDTO>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Pagination<LearnerDTO> learners = learnerService.getAllLearners(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(learners);
    }

    // Lấy learner theo ID
    @GetMapping("/{id}")
    public ResponseEntity<LearnerDTO> getById(@PathVariable String id) {
        LearnerDTO learnerDTO = learnerService.getLearnerById(id);
        return ResponseEntity.status(HttpStatus.OK).body(learnerDTO);
    }

    // Lấy tất cả courses của learner
    @GetMapping("/{id}/courses")
    public ResponseEntity<List<CourseDTO>> getCourses(@PathVariable UUID id) {
        List<CourseDTO> courseDTOList = courseService.getCoursesByLearnerId(id);
        return ResponseEntity.status(HttpStatus.OK).body(courseDTOList);
    }

    // Tìm kiếm learners theo name
    @GetMapping("/search")
    public ResponseEntity<List<LearnerDTO>> search(@RequestParam String name) {
        List<LearnerDTO> learnerDTOs = learnerService.getLearnerByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(learnerDTOs);
    }

    // Tạo learner
    @PostMapping
    public ResponseEntity<LearnerDTO> create(@Valid @RequestBody LearnerDTO learnerDTO) {
        LearnerDTO created = learnerService.createLearner(learnerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Cập nhật learner
    @PutMapping("/{id}")
    public ResponseEntity<LearnerDTO> update(@PathVariable String id, @RequestBody LearnerDTO learnerDTO) {
        LearnerDTO updated = learnerService.updateLearner(learnerDTO, id);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    // Xóa learner
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        learnerService.deleteLearner(id);
        return ResponseEntity.noContent().build();
    }
}

