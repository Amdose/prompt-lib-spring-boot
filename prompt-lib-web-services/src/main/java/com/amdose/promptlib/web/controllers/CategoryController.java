package com.amdose.promptlib.web.controllers;

import com.amdose.promptlib.database.entities.Category;
import com.amdose.promptlib.web.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public Category createCategory(@Valid @RequestBody Category category) {
        return categoryService.createCategory(category);
    }

    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable String id) {
        return categoryService.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Category not found"));
    }

    @GetMapping
//    @Cacheable(value = "getAllCategories", keyGenerator = "localizedGenerator")
    public List<Category> getAllCategories() {
        return categoryService.findAll();
    }

    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable String id, @Valid @RequestBody Category category) {
        category.setId(id);
        return categoryService.updateCategory(category);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable String id) {
        categoryService.deleteById(id);
    }

    @GetMapping("/name/{name}")
    public Category getCategoryByName(@PathVariable String name) {
        return categoryService.findByName(name)
            .orElseThrow(() -> new IllegalArgumentException("Category not found"));
    }

    @GetMapping("/order/{displayOrder}")
    public List<Category> getCategoriesByDisplayOrder(@PathVariable Integer displayOrder) {
        return categoryService.findByDisplayOrderOrderByDisplayOrderAsc(displayOrder);
    }
} 