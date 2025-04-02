package com.amdose.promptlib.web.services;

import com.amdose.promptlib.database.entities.User;
import com.amdose.promptlib.web.services.base.BaseService;

import java.util.Optional;

public interface UserService extends BaseService<User, String> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByToken(String token);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    User createUser(User user);
    User updateUser(User user);
    void incrementPromptsUsed(String userId);
    void incrementPromptsSaved(String userId);
} 