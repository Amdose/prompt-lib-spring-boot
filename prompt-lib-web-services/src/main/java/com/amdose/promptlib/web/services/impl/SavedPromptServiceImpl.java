package com.amdose.promptlib.web.services.impl;

import com.amdose.promptlib.database.entities.Prompt;
import com.amdose.promptlib.database.entities.SavedPrompt;
import com.amdose.promptlib.database.entities.User;
import com.amdose.promptlib.database.repositories.SavedPromptRepository;
import com.amdose.promptlib.web.services.PromptService;
import com.amdose.promptlib.web.services.SavedPromptService;
import com.amdose.promptlib.web.services.UserService;
import com.amdose.promptlib.web.services.base.BaseServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class SavedPromptServiceImpl extends BaseServiceImpl<SavedPrompt, String, SavedPromptRepository> implements SavedPromptService {
    private final UserService userService;
    private final PromptService promptService;

    public SavedPromptServiceImpl(SavedPromptRepository repository, UserService userService, PromptService promptService) {
        super(repository);
        this.userService = userService;
        this.promptService = promptService;
    }

    @Override
    public Page<SavedPrompt> findByUser(User user, Pageable pageable) {
        return repository.findByUser(user, pageable);
    }

    @Override
    public boolean existsByUserIdAndPromptId(String userId, String promptId) {
        return repository.existsByUserIdAndPromptId(userId, promptId);
    }

    @Override
    @Transactional
    public SavedPrompt savePrompt(String userId, String promptId) {
        if (existsByUserIdAndPromptId(userId, promptId)) {
            throw new IllegalArgumentException("Prompt is already saved by this user");
        }

        User user = userService.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Prompt prompt = promptService.findById(promptId)
            .orElseThrow(() -> new IllegalArgumentException("Prompt not found"));

        SavedPrompt savedPrompt = new SavedPrompt();
        savedPrompt.setUser(user);
        savedPrompt.setPrompt(prompt);
        savedPrompt.setSavedAt(LocalDateTime.now());

        userService.incrementPromptsSaved(userId);
        return save(savedPrompt);
    }

    @Override
    @Transactional
    public void unsavePrompt(String userId, String promptId) {
        SavedPrompt savedPrompt = repository.findByUserIdAndPromptId(userId, promptId)
            .orElseThrow(() -> new IllegalArgumentException("Saved prompt not found"));
        delete(savedPrompt);
    }
} 