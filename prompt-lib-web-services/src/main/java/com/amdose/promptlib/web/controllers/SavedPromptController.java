package com.amdose.promptlib.web.controllers;

import com.amdose.promptlib.database.entities.SavedPrompt;
import com.amdose.promptlib.database.entities.User;
import com.amdose.promptlib.web.security.SecurityUtils;
import com.amdose.promptlib.web.services.SavedPromptService;
import com.amdose.promptlib.web.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/saved-prompts")
public class SavedPromptController {
    private final SavedPromptService savedPromptService;
    private final UserService userService;

    public SavedPromptController(SavedPromptService savedPromptService, UserService userService) {
        this.savedPromptService = savedPromptService;
        this.userService = userService;
    }

    @GetMapping
    public Page<SavedPrompt> getMySavedPrompts(Pageable pageable) {
        String userId = SecurityUtils.getCurrentUserId();
        User user = userService.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return savedPromptService.findByUser(user, pageable);
    }

    @PostMapping("/{promptId}")
    public SavedPrompt savePrompt(@PathVariable String promptId) {
        return savedPromptService.savePrompt(SecurityUtils.getCurrentUserId(), promptId);
    }

    @DeleteMapping("/{promptId}")
    public void unsavePrompt(@PathVariable String promptId) {
        savedPromptService.unsavePrompt(SecurityUtils.getCurrentUserId(), promptId);
    }

    @GetMapping("/check/{promptId}")
    public boolean isPromptSaved(@PathVariable String promptId) {
        return savedPromptService.existsByUserIdAndPromptId(SecurityUtils.getCurrentUserId(), promptId);
    }
} 