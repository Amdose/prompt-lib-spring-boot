package com.amdose.promptlib.web.controllers;

import com.amdose.promptlib.database.entities.Prompt;
import com.amdose.promptlib.database.entities.User;
import com.amdose.promptlib.web.payloads.PaginatedResponse;
import com.amdose.promptlib.web.payloads.PromptResponse;
import com.amdose.promptlib.web.services.PromptService;
import com.amdose.promptlib.web.services.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prompts")
public class PromptController {
    private final PromptService promptService;
    private final UserService userService;

    public PromptController(PromptService promptService, UserService userService) {
        this.promptService = promptService;
        this.userService = userService;
    }

    @PostMapping
    public PromptResponse createPrompt(@Valid @RequestBody Prompt prompt) {
        return promptService.createPrompt(prompt);
    }

    @GetMapping("/{id}")
    public PromptResponse getPromptById(@PathVariable String id) {
        Prompt prompt = promptService.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Prompt not found"));
        return promptService.mapToResponse(prompt);
    }

    @GetMapping
    public PaginatedResponse<PromptResponse> getAllPrompts(Pageable pageable) {
        return promptService.findByStatus("published", pageable);
    }

    @PutMapping("/{id}")
    public PromptResponse updatePrompt(@PathVariable String id, @Valid @RequestBody Prompt prompt) {
        prompt.setId(id);
        return promptService.updatePrompt(prompt);
    }

    @DeleteMapping("/{id}")
    public void deletePrompt(@PathVariable String id) {
        promptService.deleteById(id);
    }

    @GetMapping("/search")
    public PaginatedResponse<PromptResponse> searchPrompts(@RequestParam String query, Pageable pageable) {
        return promptService.searchPrompts(query, pageable);
    }

    @GetMapping("/featured")
    public PaginatedResponse<PromptResponse> getFeaturedPrompts(Pageable pageable) {
        return promptService.findByIsFeaturedTrue(pageable);
    }

    @GetMapping("/trending")
    public PaginatedResponse<PromptResponse> getTrendingPrompts(Pageable pageable) {
        return promptService.findByIsTrendingTrue(pageable);
    }

    @GetMapping("/new")
    public PaginatedResponse<PromptResponse> getNewPrompts(Pageable pageable) {
        return promptService.findByIsNewTrue(pageable);
    }

    @GetMapping("/user/{userId}")
    public PaginatedResponse<PromptResponse> getPromptsByUser(@PathVariable String userId, Pageable pageable) {
        User user = userService.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return promptService.findByCreatedBy(user, pageable);
    }

    @GetMapping("/category/{categoryId}")
    public List<PromptResponse> getPromptsByCategory(@PathVariable String categoryId) {
        return promptService.findByCategoriesId(categoryId);
    }

    @GetMapping("/tag/{tagId}")
    public List<PromptResponse> getPromptsByTag(@PathVariable String tagId) {
        return promptService.findByTagsId(tagId);
    }

    @PutMapping("/{id}/featured")
    public void markAsFeatured(@PathVariable String id, @RequestParam boolean featured) {
        promptService.markAsFeatured(id, featured);
    }

    @PutMapping("/{id}/trending")
    public void markAsTrending(@PathVariable String id, @RequestParam boolean trending) {
        promptService.markAsTrending(id, trending);
    }

    @PutMapping("/{id}/new")
    public void markAsNew(@PathVariable String id, @RequestParam boolean isNew) {
        promptService.markAsNew(id, isNew);
    }

    @PutMapping("/{id}/status")
    public void updateStatus(@PathVariable String id, @RequestParam String status) {
        promptService.updateStatus(id, status);
    }
} 