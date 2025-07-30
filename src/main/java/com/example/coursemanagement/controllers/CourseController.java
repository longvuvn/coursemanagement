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
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final LearnerService learnerService;

    // Lấy tất cả khóa học có status là Active
    @GetMapping
    public ResponseEntity<Pagination<CourseDTO>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Pagination<CourseDTO> courses = courseService.getAllCourses(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(courses);
    }

    // Lấy tất cả courses theo categoryName
    @GetMapping("/categoryName")
    public ResponseEntity<Pagination<CourseDTO>> getByCategoryName(
            @RequestParam String categoryName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Pagination<CourseDTO> courses = courseService.getCoursesByCategoryName(categoryName, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(courses);
    }

    // Lấy course theo Id
    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getById(@PathVariable String id) {
        CourseDTO course = courseService.getCourseById(id);
        return ResponseEntity.status(HttpStatus.OK).body(course);
    }

    // Lấy tất cả learners trong course
    @GetMapping("/{id}/learners")
    public ResponseEntity<List<LearnerDTO>> getLearnersByCourseId(@PathVariable UUID id) {
        List<LearnerDTO> learners = learnerService.getLearnersByCourseId(id);
        return ResponseEntity.status(HttpStatus.OK).body(learners);
    }

    // Tìm kiếm course theo title
    @GetMapping("/search")
    public ResponseEntity<List<CourseDTO>> search(@RequestParam String title) {
        List<CourseDTO> courseDTOList = courseService.getCoursesByTitle(title);
        return ResponseEntity.status(HttpStatus.OK).body(courseDTOList) ;
    }

    // Lấy course mới nhất
    @GetMapping("/latest")
    public ResponseEntity<List<CourseDTO>> getLatest() {
        List<CourseDTO> courses = courseService.getLatestCourses();
        return ResponseEntity.status(HttpStatus.OK).body(courses);
    }

    // Lấy khóa học cũ nhất
    @GetMapping("/oldest")
    public ResponseEntity<List<CourseDTO>> getOldest() {
        List<CourseDTO> courses = courseService.getOldestCourses();
        return ResponseEntity.status(HttpStatus.OK).body(courses);
    }

    // Tạo course
    @PostMapping
    public ResponseEntity<CourseDTO> create(@Valid @RequestBody CourseDTO courseDTO) {
        CourseDTO created = courseService.createCourse(courseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Chỉnh sửa course
    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> update(@PathVariable String id, @RequestBody CourseDTO courseDTO) {
        CourseDTO updated = courseService.updateCourse(courseDTO, id);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    // Xóa course
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}


