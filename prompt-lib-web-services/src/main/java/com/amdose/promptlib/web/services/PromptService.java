package com.amdose.promptlib.web.services;

import com.amdose.promptlib.database.entities.Prompt;
import com.amdose.promptlib.database.entities.User;
import com.amdose.promptlib.web.payloads.PaginatedResponse;
import com.amdose.promptlib.web.payloads.PromptResponse;
import com.amdose.promptlib.web.services.base.BaseService;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PromptService extends BaseService<Prompt, String> {
    PromptResponse mapToResponse(Prompt prompt);
    PaginatedResponse<PromptResponse> findByStatus(String status, Pageable pageable);
    PaginatedResponse<PromptResponse> findByCreatedBy(User user, Pageable pageable);
    PaginatedResponse<PromptResponse> findByIsFeaturedTrue(Pageable pageable);
    PaginatedResponse<PromptResponse> findByIsTrendingTrue(Pageable pageable);
    PaginatedResponse<PromptResponse> findByIsNewTrue(Pageable pageable);
    PaginatedResponse<PromptResponse> searchPrompts(String query, Pageable pageable);
    List<PromptResponse> findByCategoriesId(String categoryId);
    List<PromptResponse> findByTagsId(String tagId);
    PromptResponse createPrompt(Prompt prompt);
    PromptResponse updatePrompt(Prompt prompt);
    void incrementUsesCount(String promptId);
    void markAsFeatured(String promptId, boolean featured);
    void markAsTrending(String promptId, boolean trending);
    void markAsNew(String promptId, boolean isNew);
    void updateStatus(String promptId, String status);
} 