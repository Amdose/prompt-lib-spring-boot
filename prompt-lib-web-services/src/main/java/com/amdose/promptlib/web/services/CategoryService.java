package com.amdose.promptlib.web.services;

import com.amdose.promptlib.database.entities.Category;
import com.amdose.promptlib.web.services.base.BaseService;

import java.util.List;
import java.util.Optional;

public interface CategoryService extends BaseService<Category, String> {
    Optional<Category> findByName(String name);
    List<Category> findByDisplayOrderOrderByDisplayOrderAsc(Integer displayOrder);
    boolean existsByName(String name);
    Category createCategory(Category category);
    Category updateCategory(Category category);
} 