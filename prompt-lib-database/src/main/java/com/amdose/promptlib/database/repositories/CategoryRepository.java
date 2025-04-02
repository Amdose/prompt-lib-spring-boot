package com.amdose.promptlib.database.repositories;

import com.amdose.promptlib.database.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    Optional<Category> findByName(String name);
    List<Category> findByDisplayOrderOrderByDisplayOrderAsc(Integer displayOrder);
    boolean existsByName(String name);
} 