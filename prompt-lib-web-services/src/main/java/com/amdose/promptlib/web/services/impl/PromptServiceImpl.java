package com.amdose.promptlib.web.services.impl;

import com.amdose.promptlib.database.entities.Category;
import com.amdose.promptlib.database.entities.Prompt;
import com.amdose.promptlib.database.entities.Tag;
import com.amdose.promptlib.database.entities.User;
import com.amdose.promptlib.database.repositories.PromptRepository;
import com.amdose.promptlib.web.payloads.PaginatedResponse;
import com.amdose.promptlib.web.payloads.PromptRequest;
import com.amdose.promptlib.web.payloads.PromptResponse;
import com.amdose.promptlib.web.payloads.SuccessResponse;
import com.amdose.promptlib.web.security.SecurityUtils;
import com.amdose.promptlib.web.services.CategoryService;
import com.amdose.promptlib.web.services.PromptService;
import com.amdose.promptlib.web.services.TagService;
import com.amdose.promptlib.web.services.UserService;
import com.amdose.promptlib.web.services.base.BaseServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PromptServiceImpl extends BaseServiceImpl<Prompt, String, PromptRepository> implements PromptService {
    private final UserService userService;
    private final CategoryService categoryService;
    private final TagService tagService;

    public PromptServiceImpl(PromptRepository repository, UserService userService, 
                           CategoryService categoryService, TagService tagService) {
        super(repository);
        this.userService = userService;
        this.categoryService = categoryService;
        this.tagService = tagService;
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
            .map(Category::getName)
            .collect(Collectors.toList()));
        response.setTags(prompt.getTags().stream()
            .map(Tag::getName)
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
    public SuccessResponse createPrompt(PromptRequest request) {
        Prompt prompt = new Prompt();
        prompt.setId(UUID.randomUUID().toString());
        prompt.setTitle(request.getTitle());
        prompt.setContent(request.getContent());
        prompt.setPreviewText(request.getPreviewText());
        prompt.setCreatedAt(new Date());
        prompt.setUpdatedAt(new Date());
        prompt.setIsNew(true);
        prompt.setIsFeatured(false);
        prompt.setIsTrending(false);
        prompt.setUsesCount(0);
        prompt.setRating(0.0);
        prompt.setStatus("published");

        // Set categories
        Set<Category> categories = request.getCategories().stream()
            .map(categoryId -> categoryService.findByName(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found: " + categoryId)))
            .collect(Collectors.toSet());
        prompt.setCategories(categories);

        // Set tags
        Set<Tag> tags = request.getTags().stream()
            .map(tagId -> tagService.findByName(tagId)
                .orElseThrow(() -> new IllegalArgumentException("Tag not found: " + tagId)))
            .collect(Collectors.toSet());
        prompt.setTags(tags);

        // Set created by
        String currentUserId = "admin";
        User user = userService.findById("admin").get();
        prompt.setCreatedBy(user);

        Prompt savedPrompt = save(prompt);
        return new SuccessResponse();
    }

    @Override
    @Transactional
    public SuccessResponse updatePrompt(String id, PromptRequest request) {
        Prompt existingPrompt = findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Prompt not found"));

        existingPrompt.setTitle(request.getTitle());
        existingPrompt.setContent(request.getContent());
        existingPrompt.setPreviewText(request.getPreviewText());
        existingPrompt.setUpdatedAt(new Date());

        // Update categories
        Set<Category> categories = request.getCategories().stream()
            .map(categoryId -> categoryService.findByName(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found: " + categoryId)))
            .collect(Collectors.toSet());
        existingPrompt.setCategories(categories);

        // Update tags
        Set<Tag> tags = request.getTags().stream()
            .map(tagId -> tagService.findByName(tagId)
                .orElseThrow(() -> new IllegalArgumentException("Tag not found: " + tagId)))
            .collect(Collectors.toSet());
        existingPrompt.setTags(tags);

        Prompt updatedPrompt = save(existingPrompt);
        return new SuccessResponse();
    }

    @Override
    @Transactional
    public SuccessResponse incrementUsesCount(String promptId) {
        Prompt prompt = findById(promptId)
            .orElseThrow(() -> new IllegalArgumentException("Prompt not found"));
        prompt.setUsesCount(prompt.getUsesCount() + 1);
        save(prompt);
        return new SuccessResponse();
    }

    @Override
    @Transactional
    public SuccessResponse markAsFeatured(String promptId, boolean featured) {
        Prompt prompt = findById(promptId)
            .orElseThrow(() -> new IllegalArgumentException("Prompt not found"));
        prompt.setIsFeatured(featured);
        prompt.setUpdatedAt(new Date());
        save(prompt);
        return new SuccessResponse();
    }

    @Override
    @Transactional
    public SuccessResponse markAsTrending(String promptId, boolean trending) {
        Prompt prompt = findById(promptId)
            .orElseThrow(() -> new IllegalArgumentException("Prompt not found"));
        prompt.setIsTrending(trending);
        prompt.setUpdatedAt(new Date());
        save(prompt);
        return new SuccessResponse();
    }

    @Override
    @Transactional
    public SuccessResponse markAsNew(String promptId, boolean isNew) {
        Prompt prompt = findById(promptId)
            .orElseThrow(() -> new IllegalArgumentException("Prompt not found"));
        prompt.setIsNew(isNew);
        prompt.setUpdatedAt(new Date());
        save(prompt);
        return new SuccessResponse();
    }

    @Override
    @Transactional
    public SuccessResponse updateStatus(String promptId, String status) {
        Prompt prompt = findById(promptId)
            .orElseThrow(() -> new IllegalArgumentException("Prompt not found"));
        prompt.setStatus(status);
        prompt.setUpdatedAt(new Date());
        save(prompt);
        return new SuccessResponse();
    }
} 