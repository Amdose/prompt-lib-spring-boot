package com.amdose.promptlib.web.services.impl;

import com.amdose.promptlib.database.entities.Category;
import com.amdose.promptlib.database.repositories.CategoryRepository;
import com.amdose.promptlib.web.services.CategoryService;
import com.amdose.promptlib.web.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl extends BaseServiceImpl<Category, String, CategoryRepository> implements CategoryService {
    public CategoryServiceImpl(CategoryRepository repository) {
        super(repository);
    }

    @Override
    public Optional<Category> findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public List<Category> findByDisplayOrderOrderByDisplayOrderAsc(Integer displayOrder) {
        return repository.findByDisplayOrderOrderByDisplayOrderAsc(displayOrder);
    }

    @Override
    public boolean existsByName(String name) {
        return repository.existsByName(name);
    }

    @Override
    @Transactional
    public Category createCategory(Category category) {
        if (existsByName(category.getName())) {
            throw new IllegalArgumentException("Category name already exists");
        }

        category.setCreatedAt(new Date());
        category.setUpdatedAt(new Date());
        return save(category);
    }

    @Override
    @Transactional
    public Category updateCategory(Category category) {
        Category existingCategory = findById(category.getId())
            .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        if (!existingCategory.getName().equals(category.getName()) && existsByName(category.getName())) {
            throw new IllegalArgumentException("Category name already exists");
        }

        category.setUpdatedAt(new Date());
        return save(category);
    }
} 