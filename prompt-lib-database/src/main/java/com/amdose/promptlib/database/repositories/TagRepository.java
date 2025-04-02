package com.amdose.promptlib.database.repositories;

import com.amdose.promptlib.database.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, String> {
    Optional<Tag> findByName(String name);
    boolean existsByName(String name);
} 