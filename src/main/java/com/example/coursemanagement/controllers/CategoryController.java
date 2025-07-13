package com.example.coursemanagement.controllers;


import com.example.coursemanagement.models.APIResponse;
import com.example.coursemanagement.models.dto.CategoryDTO;
import com.example.coursemanagement.services.CategoryService;
import com.example.coursemanagement.services.exceptions.errors.BadRequestException;
import com.example.coursemanagement.services.exceptions.errors.ResourceNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    //lấy tất cả category
    @GetMapping
    public ResponseEntity<APIResponse<List<CategoryDTO>>> getAll() {
        try{
            List<CategoryDTO> categories = categoryService.getAllCategories();
            APIResponse<List<CategoryDTO>> response = new APIResponse<>(
                    "success",
                    "Categories retrieved successfully",
                    categories,
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.ok(response);
        }catch (ResourceNotFoundException ex){
            APIResponse<List<CategoryDTO>> response = new APIResponse<>(
                    "error",
                    "Categories retrieved failed",
                    null,
                    Map.of("error", ex.getMessage()),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    //lâ category theo id
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<CategoryDTO>> getById(@Valid @PathVariable String id) {
        try{
            CategoryDTO category = categoryService.getCategoryById(id);
            APIResponse<CategoryDTO> response = new APIResponse<>(
                    "success",
                    "Category retrieved successfully",
                    category,
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.ok(response);
        }catch (ResourceNotFoundException ex){
            APIResponse<CategoryDTO> response = new APIResponse<>(
                    "error",
                    "Category not found",
                    null,
                    Map.of("error", ex.getMessage()) ,
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    //tạo category
    @PostMapping
    public ResponseEntity<APIResponse<CategoryDTO>> create(@Valid @RequestBody CategoryDTO categoryDTO) {
        try{
            CategoryDTO created = categoryService.createCategory(categoryDTO);
            APIResponse<CategoryDTO> response = new APIResponse<>(
                    "success",
                    "Category created successfully",
                    created,
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (BadRequestException ex){
            APIResponse<CategoryDTO> response = new APIResponse<>(
                    "error",
                    "Category created failed",
                    null,
                    Map.of("error", ex.getMessage()),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    //chỉnh sửa category
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<CategoryDTO>> update(@PathVariable String id, @RequestBody CategoryDTO categoryDTO) {
        try{
            CategoryDTO updated = categoryService.updateCategory(categoryDTO, id);
            APIResponse<CategoryDTO> response = new APIResponse<>(
                    "success",
                    "Category updated successfully",
                    updated,
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.ok(response);
        }catch (ResourceNotFoundException ex){
            APIResponse<CategoryDTO> response = new APIResponse<>(
                    "error",
                    "Category updated failed",
                    null,
                    Map.of("error", ex.getMessage()),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    //xóa category
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> delete(@PathVariable String id) {
        try {
            categoryService.deleteCategory(id);
            APIResponse<Void> response = new APIResponse<>(
                    "success",
                    "Category deleted successfully",
                    null,
                    null,
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        } catch (ResourceNotFoundException ex) {
            APIResponse<Void> response = new APIResponse<>(
                    "error",
                    "Category deletion failed",
                    null,
                    Map.of("error", ex.getMessage()),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
