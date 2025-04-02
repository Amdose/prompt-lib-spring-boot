package com.amdose.promptlib.web.services;

import com.amdose.promptlib.database.entities.Tag;
import com.amdose.promptlib.web.services.base.BaseService;

import java.util.Optional;

public interface TagService extends BaseService<Tag, String> {
    Optional<Tag> findByName(String name);
    boolean existsByName(String name);
    Tag createTag(Tag tag);
    Tag updateTag(Tag tag);
} 