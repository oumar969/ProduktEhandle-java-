package com.ehandle.controller;

import com.ehandle.dto.CategoryDTO;
import com.ehandle.model.Category;
import com.ehandle.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/categories")
@Tag(name = "Categories", description = "Product category management endpoints")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    @Operation(summary = "Get all categories", description = "Retrieve all product categories")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategories()
                .stream()
                .map(categoryService::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get category by ID", description = "Retrieve a specific category")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(categoryService.toDTO(category));
    }

    @PostMapping
    @Operation(summary = "Create new category", description = "Create a new product category")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        log.info("Creating new category: {}", categoryDTO.getName());
        Category category = categoryService.toEntity(categoryDTO);
        Category created = categoryService.createCategory(category);
        return new ResponseEntity<>(categoryService.toDTO(created), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update category", description = "Update an existing category")
    public ResponseEntity<CategoryDTO> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryDTO categoryDTO) {
        log.info("Updating category: {}", id);
        Category category = categoryService.toEntity(categoryDTO);
        Category updated = categoryService.updateCategory(id, category);
        return ResponseEntity.ok(categoryService.toDTO(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete category", description = "Delete a product category")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        log.info("Deleting category: {}", id);
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
