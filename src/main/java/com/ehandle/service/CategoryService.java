package com.ehandle.service;

import com.ehandle.dto.CategoryDTO;
import com.ehandle.exception.BadRequestException;
import com.ehandle.exception.ResourceNotFoundException;
import com.ehandle.model.Category;
import com.ehandle.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category createCategory(Category category) {
        if (categoryRepository.findByName(category.getName()).isPresent()) {
            throw new BadRequestException("Category with name " + category.getName() + " already exists");
        }
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, Category categoryDetails) {
        Category category = getCategoryById(id);

        if (categoryDetails.getName() != null && !categoryDetails.getName().equals(category.getName())) {
            if (categoryRepository.findByName(categoryDetails.getName()).isPresent()) {
                throw new BadRequestException("Category with name " + categoryDetails.getName() + " already exists");
            }
            category.setName(categoryDetails.getName());
        }

        if (categoryDetails.getDescription() != null) {
            category.setDescription(categoryDetails.getDescription());
        }

        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        Category category = getCategoryById(id);
        categoryRepository.delete(category);
    }

    public CategoryDTO toDTO(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }

    public Category toEntity(CategoryDTO categoryDTO) {
        return Category.builder()
                .name(categoryDTO.getName())
                .description(categoryDTO.getDescription())
                .build();
    }
}
