package com.amdose.promptlib.database.repositories;

import com.amdose.promptlib.database.entities.SavedPrompt;
import com.amdose.promptlib.database.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface SavedPromptRepository extends JpaRepository<SavedPrompt, String> {
    Page<SavedPrompt> findByUser(User user, Pageable pageable);
    boolean existsByUserIdAndPromptId(String userId, String promptId);
    Optional<SavedPrompt> findByUserIdAndPromptId(String userId, String promptId);
} 