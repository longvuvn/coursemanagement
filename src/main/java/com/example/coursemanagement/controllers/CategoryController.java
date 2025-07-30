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

    //get all categories
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAll() {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        return ResponseEntity.status(HttpStatus.OK).body(categories);
    }

    //get category by id
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getById(@Valid @PathVariable String id) {
        CategoryDTO category = categoryService.getCategoryById(id);
        return ResponseEntity.status(HttpStatus.OK).body(category);
    }

    //created category
    @PostMapping
    public ResponseEntity<CategoryDTO> create(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO created = categoryService.createCategory(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    //update category
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> update(@PathVariable String id, @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO updated = categoryService.updateCategory(categoryDTO, id);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    //delete category
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
