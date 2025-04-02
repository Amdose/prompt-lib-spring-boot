package com.amdose.promptlib.web.controllers;

import com.amdose.promptlib.database.entities.User;
import com.amdose.promptlib.web.exceptions.InvalidRequestException;
import com.amdose.promptlib.web.payloads.*;
import com.amdose.promptlib.web.security.UserDetailsImpl;
import com.amdose.promptlib.web.services.JwtService;
import com.amdose.promptlib.web.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(UserService userService, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public TokenResponse login(@RequestBody LoginRequest request) {
        Optional<User> userOptional = userService.findByEmail(request.getEmail());
        
        if (userOptional.isEmpty()) {
            throw new InvalidRequestException("user-not-found", "User not found. Please register first.");
        }

        User user = userOptional.get();
        
        // Verify password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidRequestException("invalid-password", "Invalid password");
        }

        // Generate JWT token
        String token = jwtService.generateToken(new UserDetailsImpl(user));
        
        return new TokenResponse(token);
    }

    @PostMapping("/register")
    public AcknowledgeResponse register(@RequestBody RegisterRequest request) {
        // Check if email already exists
        if (userService.existsByEmail(request.getEmail())) {
            throw new InvalidRequestException("email-exists", "Email already registered");
        }

        if (userService.existsByUsername(request.getUsername())) {
            throw new InvalidRequestException("username-exists", "Username already taken");
        }

        // Create new user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setPromptsUsed(0);
        user.setPromptsSaved(0);

        try {
            userService.createUser(user);
            return new SuccessResponse("User registered successfully");
        } catch (IllegalArgumentException e) {
            throw new InvalidRequestException("registration-failed", e.getMessage());
        }
    }
} 