package com.example.coursemanagement.services.impl;


import com.example.coursemanagement.enums.CategoryStatus;
import com.example.coursemanagement.models.Category;
import com.example.coursemanagement.models.dto.CategoryDTO;
import com.example.coursemanagement.repositories.CategoryRepository;
import com.example.coursemanagement.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public CategoryDTO getCategoryById(String id) {
        UUID uuid = UUID.fromString(id);
        Optional<Category> optionalCategory = categoryRepository.findById(uuid);
        return optionalCategory.map(category -> modelMapper.map(category, CategoryDTO.class)).orElse(null);
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        category.setName(categoryDTO.getName());
        category.setStatus(CategoryStatus.ACTIVE);
        return modelMapper.map(categoryRepository.save(category), CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, String id) {
        UUID uuid = UUID.fromString(id);
        Category existingCategory = categoryRepository.findById(uuid).orElse(null);
        existingCategory.setName(categoryDTO.getName());
        existingCategory.setStatus(CategoryStatus.valueOf(categoryDTO.getStatus()));
        return modelMapper.map(categoryRepository.save(existingCategory), CategoryDTO.class);
    }

    @Override
    public void deleteCategory(String id) {
        UUID uuid = UUID.fromString(id);
        Category existingCategory = categoryRepository.findById(uuid).orElse(null);
        categoryRepository.delete(existingCategory);
    }

}
