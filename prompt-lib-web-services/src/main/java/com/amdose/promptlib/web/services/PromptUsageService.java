package com.amdose.promptlib.web.services;

import com.amdose.promptlib.database.entities.PromptUsage;
import com.amdose.promptlib.database.entities.User;
import com.amdose.promptlib.web.services.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PromptUsageService extends BaseService<PromptUsage, String> {
    Page<PromptUsage> findByUser(User user, Pageable pageable);
    Page<PromptUsage> findByPromptId(String promptId, Pageable pageable);
    PromptUsage recordUsage(String userId, String promptId, String actionType);
} 