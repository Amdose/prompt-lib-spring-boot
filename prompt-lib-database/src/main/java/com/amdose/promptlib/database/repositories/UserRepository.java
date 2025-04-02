package com.amdose.promptlib.database.repositories;

import com.amdose.promptlib.database.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByToken(String token);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
} 