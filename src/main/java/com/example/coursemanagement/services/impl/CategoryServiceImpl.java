package com.example.coursemanagement.services.impl;


import com.example.coursemanagement.enums.CategoryStatus;
import com.example.coursemanagement.models.Category;
import com.example.coursemanagement.models.dto.CategoryDTO;
import com.example.coursemanagement.repositories.CategoryRepository;
import com.example.coursemanagement.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(this::categoryToCategoryDTO)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public CategoryDTO getCategoryById(String id) {
        UUID uuid = UUID.fromString(id);
        Optional<Category> category = categoryRepository.findById(uuid);
        return category.map(this::categoryToCategoryDTO).orElse(null);
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setStatus(CategoryStatus.ACTIVE);
        return categoryToCategoryDTO(categoryRepository.save(category));
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, String id) {
        UUID uuid = UUID.fromString(id);
        Category existingCategory = categoryRepository.findById(uuid).orElse(null);
        existingCategory.setName(categoryDTO.getName());
        existingCategory.setStatus(CategoryStatus.valueOf(categoryDTO.getStatus()));
        return categoryToCategoryDTO(categoryRepository.save(existingCategory));
    }

    @Override
    public void deleteCategory(String id) {
        UUID uuid = UUID.fromString(id);
        Category existingCategory = categoryRepository.findById(uuid).orElse(null);
        categoryRepository.delete(existingCategory);
    }

    public CategoryDTO categoryToCategoryDTO(Category category){
        CategoryDTO dto = new CategoryDTO();
        dto.setId(String.valueOf(category.getId()));
        dto.setName(category.getName());
        dto.setStatus(category.getStatus().name());
        return dto;
    }
}
