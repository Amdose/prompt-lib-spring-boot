package com.amdose.promptlib.web.controllers;

import com.amdose.promptlib.database.entities.PromptUsage;
import com.amdose.promptlib.database.entities.User;
import com.amdose.promptlib.web.services.PromptUsageService;
import com.amdose.promptlib.web.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/prompt-usage")
public class PromptUsageController {
    private final PromptUsageService promptUsageService;
    private final UserService userService;

    public PromptUsageController(PromptUsageService promptUsageService, UserService userService) {
        this.promptUsageService = promptUsageService;
        this.userService = userService;
    }

    @GetMapping("/user/{userId}")
    public Page<PromptUsage> getPromptUsageByUser(@PathVariable String userId, Pageable pageable) {
        User user = userService.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return promptUsageService.findByUser(user, pageable);
    }

    @GetMapping("/prompt/{promptId}")
    public Page<PromptUsage> getPromptUsageByPrompt(@PathVariable String promptId, Pageable pageable) {
        return promptUsageService.findByPromptId(promptId, pageable);
    }

    @PostMapping("/record")
    public PromptUsage recordUsage(
            @RequestParam String userId,
            @RequestParam String promptId,
            @RequestParam String actionType) {
        return promptUsageService.recordUsage(userId, promptId, actionType);
    }
} 