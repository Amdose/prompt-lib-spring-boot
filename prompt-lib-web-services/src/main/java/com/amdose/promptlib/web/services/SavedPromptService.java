package com.amdose.promptlib.web.services;

import com.amdose.promptlib.database.entities.SavedPrompt;
import com.amdose.promptlib.database.entities.User;
import com.amdose.promptlib.web.services.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SavedPromptService extends BaseService<SavedPrompt, String> {
    Page<SavedPrompt> findByUser(User user, Pageable pageable);
    boolean existsByUserIdAndPromptId(String userId, String promptId);
    SavedPrompt savePrompt(String userId, String promptId);
    void unsavePrompt(String userId, String promptId);
} 