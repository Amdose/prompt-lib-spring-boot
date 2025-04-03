package com.amdose.promptlib.web.services.impl;

import com.amdose.promptlib.database.entities.Prompt;
import com.amdose.promptlib.database.entities.PromptUsage;
import com.amdose.promptlib.database.entities.User;
import com.amdose.promptlib.database.repositories.PromptUsageRepository;
import com.amdose.promptlib.web.services.PromptService;
import com.amdose.promptlib.web.services.PromptUsageService;
import com.amdose.promptlib.web.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PromptUsageServiceImpl implements PromptUsageService {
    private final PromptUsageRepository repository;
    private final UserService userService;
    private final PromptService promptService;

    public PromptUsageServiceImpl(PromptUsageRepository repository, UserService userService, PromptService promptService) {
        this.repository = repository;
        this.userService = userService;
        this.promptService = promptService;
    }

    @Override
    @Transactional
    public PromptUsage save(PromptUsage entity) {
        return repository.save(entity);
    }

    @Override
    @Transactional
    public List<PromptUsage> saveAll(List<PromptUsage> entities) {
        return repository.saveAll(entities);
    }

    @Override
    public Optional<PromptUsage> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<PromptUsage> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void delete(PromptUsage entity) {
        repository.delete(entity);
    }

    @Override
    public boolean existsById(String id) {
        return repository.existsById(id);
    }

    @Override
    public Page<PromptUsage> findByUser(User user, Pageable pageable) {
        return repository.findByUser(user, pageable);
    }

    @Override
    public Page<PromptUsage> findByPromptId(String promptId, Pageable pageable) {
        return repository.findByPromptId(promptId, pageable);
    }

    @Override
    @Transactional
    public PromptUsage recordUsage(String userId, String promptId, String actionType) {
        User user = userService.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Prompt prompt = promptService.findById(promptId)
            .orElseThrow(() -> new IllegalArgumentException("Prompt not found"));

        PromptUsage usage = new PromptUsage();
        usage.setId(UUID.randomUUID().toString());
        usage.setUser(user);
        usage.setPrompt(prompt);
        usage.setActionType(actionType);
        usage.setUsedAt(new Date());

        userService.incrementPromptsUsed(userId);
        promptService.incrementUsesCount(promptId);

        return save(usage);
    }
} 