package com.amdose.promptlib.database.repositories;

import com.amdose.promptlib.database.entities.Prompt;
import com.amdose.promptlib.database.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface PromptRepository extends JpaRepository<Prompt, String> {
    Page<Prompt> findByStatus(String status, Pageable pageable);
    Page<Prompt> findByCreatedBy(User user, Pageable pageable);
    Page<Prompt> findByIsFeaturedTrue(Pageable pageable);
    Page<Prompt> findByIsTrendingTrue(Pageable pageable);
    Page<Prompt> findByIsNewTrue(Pageable pageable);
    
    @Query("SELECT p FROM Prompt p WHERE p.status = 'published' AND (LOWER(p.title) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(p.content) LIKE LOWER(CONCAT('%', :query, '%')))")
    Page<Prompt> searchPrompts(String query, Pageable pageable);
    
    List<Prompt> findByCategoriesId(String categoryId);
    List<Prompt> findByTagsId(String tagId);
} 