package com.amdose.promptlib.database.repositories;

import com.amdose.promptlib.database.entities.PromptUsage;
import com.amdose.promptlib.database.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface PromptUsageRepository extends JpaRepository<PromptUsage, String> {
    Page<PromptUsage> findByUser(User user, Pageable pageable);
    Page<PromptUsage> findByPromptId(String promptId, Pageable pageable);
} 