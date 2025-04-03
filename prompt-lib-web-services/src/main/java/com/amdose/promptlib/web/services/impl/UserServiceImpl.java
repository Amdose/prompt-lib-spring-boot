package com.amdose.promptlib.web.services.impl;

import com.amdose.promptlib.database.entities.User;
import com.amdose.promptlib.database.repositories.UserRepository;
import com.amdose.promptlib.web.services.UserService;
import com.amdose.promptlib.web.services.base.BaseServiceImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, String, UserRepository> implements UserService {
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder) {
        super(repository);
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public Optional<User> findByToken(String token) {
        return repository.findByToken(token);
    }

    @Override
    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    @Transactional
    public User createUser(User user) {
        if (existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        user.setId(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        return save(user);
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        User existingUser = findById(user.getId())
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!existingUser.getUsername().equals(user.getUsername()) && existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (!existingUser.getEmail().equals(user.getEmail()) && existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            user.setPassword(existingUser.getPassword());
        }

        user.setUpdatedAt(new Date());
        return save(user);
    }

    @Override
    @Transactional
    public void incrementPromptsUsed(String userId) {
        User user = findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setPromptsUsed(user.getPromptsUsed() + 1);
        save(user);
    }

    @Override
    @Transactional
    public void incrementPromptsSaved(String userId) {
        User user = findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setPromptsSaved(user.getPromptsSaved() + 1);
        save(user);
    }
} 