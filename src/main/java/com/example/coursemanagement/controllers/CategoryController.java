package com.example.coursemanagement.controllers;


import com.example.coursemanagement.models.APIResponse;
import com.example.coursemanagement.models.dto.CategoryDTO;
import com.example.coursemanagement.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    //lấy tất cả category
    @GetMapping
    public ResponseEntity<APIResponse<List<CategoryDTO>>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        APIResponse<List<CategoryDTO>> response = new APIResponse<>(
                "success",
                "Categories retrieved successfully",
                categories,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

    //lâ category theo id
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<CategoryDTO>> getCategoryById(@PathVariable String id) {
        CategoryDTO category = categoryService.getCategoryById(id);
        APIResponse<CategoryDTO> response = new APIResponse<>(
                "success",
                "Category retrieved successfully",
                category,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

    //tạo category
    @PostMapping
    public ResponseEntity<APIResponse<CategoryDTO>> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO created = categoryService.createCategory(categoryDTO);
        APIResponse<CategoryDTO> response = new APIResponse<>(
                "success",
                "Category created successfully",
                created,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //chỉnh sửa category
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<CategoryDTO>> updateCategory(@PathVariable String id, @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO updated = categoryService.updateCategory(categoryDTO, id);
        APIResponse<CategoryDTO> response = new APIResponse<>(
                "success",
                "Category updated successfully",
                updated,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(response);
    }

    //xóa category
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> deleteCategory(@PathVariable String id) {
        categoryService.deleteCategory(id);
        APIResponse<Void> response = new APIResponse<>(
                "success",
                "Category deleted successfully",
                null,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}
