package com.amdose.promptlib.web.services.impl;

import com.amdose.promptlib.database.entities.Category;
import com.amdose.promptlib.database.entities.Prompt;
import com.amdose.promptlib.database.entities.Tag;
import com.amdose.promptlib.database.entities.User;
import com.amdose.promptlib.database.repositories.PromptRepository;
import com.amdose.promptlib.web.payloads.PaginatedResponse;
import com.amdose.promptlib.web.payloads.PromptResponse;
import com.amdose.promptlib.web.services.PromptService;
import com.amdose.promptlib.web.services.base.BaseServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PromptServiceImpl extends BaseServiceImpl<Prompt, String, PromptRepository> implements PromptService {
    public PromptServiceImpl(PromptRepository repository) {
        super(repository);
    }

    public PromptResponse mapToResponse(Prompt prompt) {
        PromptResponse response = new PromptResponse();
        response.setId(prompt.getId());
        response.setTitle(prompt.getTitle());
        response.setContent(prompt.getContent());
        response.setPreviewText(prompt.getPreviewText());
        response.setRating(prompt.getRating());
        response.setUsesCount(prompt.getUsesCount());
        response.setCreatedBy(prompt.getCreatedBy().getId());
        response.setFeatured(prompt.getIsFeatured());
        response.setTrending(prompt.getIsTrending());
        response.setNew(prompt.getIsNew());
        response.setStatus(prompt.getStatus());
        response.setCategories(prompt.getCategories().stream()
            .map(Category::getId)
            .collect(Collectors.toList()));
        response.setTags(prompt.getTags().stream()
            .map(Tag::getId)
            .collect(Collectors.toList()));
        response.setCreatedAt(prompt.getCreatedAt());
        response.setUpdatedAt(prompt.getUpdatedAt());
        return response;
    }

    @Override
    public PaginatedResponse<PromptResponse> findByStatus(String status, Pageable pageable) {
        Page<Prompt> page = repository.findByStatus(status, pageable);
        List<PromptResponse> responses = page.getContent().stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
        return new PaginatedResponse<>(responses, page.getTotalElements());
    }

    @Override
    public PaginatedResponse<PromptResponse> findByCreatedBy(User user, Pageable pageable) {
        Page<Prompt> page = repository.findByCreatedBy(user, pageable);
        List<PromptResponse> responses = page.getContent().stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
        return new PaginatedResponse<>(responses, page.getTotalElements());
    }

    @Override
    public PaginatedResponse<PromptResponse> findByIsFeaturedTrue(Pageable pageable) {
        Page<Prompt> page = repository.findByIsFeaturedTrue(pageable);
        List<PromptResponse> responses = page.getContent().stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
        return new PaginatedResponse<>(responses, page.getTotalElements());
    }

    @Override
    public PaginatedResponse<PromptResponse> findByIsTrendingTrue(Pageable pageable) {
        Page<Prompt> page = repository.findByIsTrendingTrue(pageable);
        List<PromptResponse> responses = page.getContent().stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
        return new PaginatedResponse<>(responses, page.getTotalElements());
    }

    @Override
    public PaginatedResponse<PromptResponse> findByIsNewTrue(Pageable pageable) {
        Page<Prompt> page = repository.findByIsNewTrue(pageable);
        List<PromptResponse> responses = page.getContent().stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
        return new PaginatedResponse<>(responses, page.getTotalElements());
    }

    @Override
    public PaginatedResponse<PromptResponse> searchPrompts(String query, Pageable pageable) {
        Page<Prompt> page = repository.searchPrompts(query, pageable);
        List<PromptResponse> responses = page.getContent().stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
        return new PaginatedResponse<>(responses, page.getTotalElements());
    }

    @Override
    public List<PromptResponse> findByCategoriesId(String categoryId) {
        return repository.findByCategoriesId(categoryId).stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    @Override
    public List<PromptResponse> findByTagsId(String tagId) {
        return repository.findByTagsId(tagId).stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PromptResponse createPrompt(Prompt prompt) {
        prompt.setCreatedAt(LocalDateTime.now());
        prompt.setUpdatedAt(LocalDateTime.now());
        prompt.setIsNew(true);
        prompt.setIsFeatured(false);
        prompt.setIsTrending(false);
        prompt.setUsesCount(0);
        prompt.setRating(0.0);
        return mapToResponse(save(prompt));
    }

    @Override
    @Transactional
    public PromptResponse updatePrompt(Prompt prompt) {
        Prompt existingPrompt = findById(prompt.getId())
            .orElseThrow(() -> new IllegalArgumentException("Prompt not found"));

        prompt.setUpdatedAt(LocalDateTime.now());
        return mapToResponse(save(prompt));
    }

    @Override
    @Transactional
    public void incrementUsesCount(String promptId) {
        Prompt prompt = findById(promptId)
            .orElseThrow(() -> new IllegalArgumentException("Prompt not found"));
        prompt.setUsesCount(prompt.getUsesCount() + 1);
        save(prompt);
    }

    @Override
    @Transactional
    public void markAsFeatured(String promptId, boolean featured) {
        Prompt prompt = findById(promptId)
            .orElseThrow(() -> new IllegalArgumentException("Prompt not found"));
        prompt.setIsFeatured(featured);
        prompt.setUpdatedAt(LocalDateTime.now());
        save(prompt);
    }

    @Override
    @Transactional
    public void markAsTrending(String promptId, boolean trending) {
        Prompt prompt = findById(promptId)
            .orElseThrow(() -> new IllegalArgumentException("Prompt not found"));
        prompt.setIsTrending(trending);
        prompt.setUpdatedAt(LocalDateTime.now());
        save(prompt);
    }

    @Override
    @Transactional
    public void markAsNew(String promptId, boolean isNew) {
        Prompt prompt = findById(promptId)
            .orElseThrow(() -> new IllegalArgumentException("Prompt not found"));
        prompt.setIsNew(isNew);
        prompt.setUpdatedAt(LocalDateTime.now());
        save(prompt);
    }

    @Override
    @Transactional
    public void updateStatus(String promptId, String status) {
        Prompt prompt = findById(promptId)
            .orElseThrow(() -> new IllegalArgumentException("Prompt not found"));
        prompt.setStatus(status);
        prompt.setUpdatedAt(LocalDateTime.now());
        save(prompt);
    }
} 