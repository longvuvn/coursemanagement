package com.example.coursemanagement.controllers;

import com.example.coursemanagement.models.APIResponse;
import com.example.coursemanagement.models.Pagination;
import com.example.coursemanagement.models.dto.CourseDTO;
import com.example.coursemanagement.models.dto.LearnerDTO;
import com.example.coursemanagement.services.CourseService;
import com.example.coursemanagement.services.LearnerService;
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
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final LearnerService learnerService;

    //lấy tất cả khóa học có status là Active
    @GetMapping()
    public ResponseEntity<APIResponse<Pagination<CourseDTO>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){

        try{
            Pagination<CourseDTO> courses = courseService.getAllCourses(page, size);
            APIResponse<Pagination<CourseDTO>> response = new APIResponse<>(
                    "success",
                    "Courses retrieved successfully",
                    courses,
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.ok(response);
        }catch (ResourceNotFoundException ex){
            APIResponse<Pagination<CourseDTO>> response = new APIResponse<>(
                    "error",
                    "Courses retrieved false",
                    null,
                    Map.of("không tìm thấy dữ liệu", "thử lại"),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    //lấy tất cả courses theo categoryName
    @GetMapping("/categoryName")
    public ResponseEntity<APIResponse<Pagination<CourseDTO>>> getByCategoryName(
            @RequestParam String categoryName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        try{
            Pagination<CourseDTO> courses = courseService.getCoursesByCategoryName(categoryName,page, size);
            APIResponse<Pagination<CourseDTO>> response = new APIResponse<>(
                    "success",
                    "Courses retrieved successfully",
                    courses,
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.ok(response);
        }catch (ResourceNotFoundException ex){
            APIResponse<Pagination<CourseDTO>> response = new APIResponse<>(
                    "error",
                    "Courses retrieved failed",
                    null,
                    Map.of("error", ex.getMessage()),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    //lấy course theo Id
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<CourseDTO>> getById(@PathVariable String id) {
        try{
            CourseDTO course = courseService.getCourseById(id);
            APIResponse<CourseDTO> response = new APIResponse<>(
                    "success",
                    "Course retrieved successfully",
                    course,
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.ok(response);
        }catch (ResourceNotFoundException ex){
            APIResponse<CourseDTO> response = new APIResponse<>(
                    "error",
                    "Courses retrieved failed",
                    null,
                    Map.of("error", ex.getMessage()),
                    LocalDateTime.now()
            );

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    //lấy tất cả learners trong course
    @GetMapping("/{id}/learners")
    public ResponseEntity<APIResponse<List<LearnerDTO>>> getLearnersByCourseId(@PathVariable UUID id) {
        try{
            List<LearnerDTO> learners = learnerService.getLearnersByCourseId(id);
            APIResponse<List<LearnerDTO>> response = new APIResponse<>(
                    "success",
                    "Learners retrieved successfully",
                    learners,
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.ok(response);
        }catch (ResourceNotFoundException ex){
            APIResponse<List<LearnerDTO>> response = new APIResponse<>(
                    "error",
                    "Learners retrieved failed",
                    null,
                    Map.of("error", ex.getMessage()),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    //tìm kiếm course theo title
    @GetMapping("/search")
    public ResponseEntity<APIResponse<List<CourseDTO>>> search (@RequestParam String title){
        try{
            List<CourseDTO> courseDTOList = courseService.getCoursesByTitle(title);
            APIResponse<List<CourseDTO>> response = new APIResponse<>(
                    "success",
                    "Courses retrieved successfully",
                    courseDTOList,
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.status(200).body(response);
        }catch (ResourceNotFoundException ex){
            APIResponse<List<CourseDTO>> response = new APIResponse<>(
                    "error",
                    "Courses not found",
                    null,
                    Map.of("error", ex.getMessage()),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    //lấy course mới nhất
    @GetMapping("/latest")
    public ResponseEntity<APIResponse<List<CourseDTO>>> getLatest() {
        try{
            List<CourseDTO> courses = courseService.getLatestCourses();
            APIResponse<List<CourseDTO>> response = new APIResponse<>(
                    "success",
                    "Courses retrieved successfully",
                    courses,
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.ok(response);
        }catch (ResourceNotFoundException ex){
            APIResponse<List<CourseDTO>> response = new APIResponse<>(
                    "error",
                    "Courses retrieved failed",
                    null,
                    Map.of("error", ex.getMessage()),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    //lấy khóa học cũ nhất
    @GetMapping("/oldest")
    public ResponseEntity<APIResponse<List<CourseDTO>>> getOldest() {
        try{
            List<CourseDTO> courses = courseService.getOldestCourses();
            APIResponse<List<CourseDTO>> response = new APIResponse<>(
                    "success",
                    "Courses retrieved successfully",
                    courses,
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.ok(response);
        }catch (ResourceNotFoundException ex){
            APIResponse<List<CourseDTO>> response = new APIResponse<>(
                    "error",
                    "Courses retrieved failed",
                    null,
                    Map.of("error", ex.getMessage()),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    //tạo course
    @PostMapping
    public ResponseEntity<APIResponse<CourseDTO>> create(@Valid @RequestBody CourseDTO courseDTO) {
        try{
            CourseDTO created = courseService.createCourse(courseDTO);
            APIResponse<CourseDTO> response = new APIResponse<>(
                    "success",
                    "Course created successfully",
                    created,
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (BadRequestException ex){
            APIResponse<CourseDTO> response = new APIResponse<>(
                    "error",
                    "Course created failed",
                    null,
                    Map.of("error", ex.getMessage()),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    //chỉnh sửa course
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<CourseDTO>> update(@PathVariable String id, @RequestBody CourseDTO courseDTO) {
        try{
            CourseDTO updated = courseService.updateCourse(courseDTO, id);
            APIResponse<CourseDTO> response = new APIResponse<>(
                    "success",
                    "Course updated successfully",
                    updated,
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.ok(response);
        }catch (ResourceNotFoundException ex){
            APIResponse<CourseDTO> response = new APIResponse<>(
                    "error",
                    "Course updated failed",
                    null,
                    Map.of("error", ex.getMessage()),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    //xóa course
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> delete(@PathVariable String id) {
        try{
            courseService.deleteCourse(id);
            APIResponse<Void> response = new APIResponse<>(
                    "success",
                    "Course deleted successfully",
                    null,
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }catch (ResourceNotFoundException ex){
            APIResponse<Void> response = new APIResponse<>(
                    "error",
                    "Course deleted failed",
                    null,
                    Map.of("error", ex.getMessage()),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}

