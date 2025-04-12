package com.amdose.promptlib.web.services.impl;

import com.amdose.promptlib.database.entities.Tag;
import com.amdose.promptlib.database.repositories.TagRepository;
import com.amdose.promptlib.web.services.TagService;
import com.amdose.promptlib.web.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TagServiceImpl extends BaseServiceImpl<Tag, String, TagRepository> implements TagService {
    public TagServiceImpl(TagRepository repository) {
        super(repository);
    }

    @Override
    @Transactional
    public Tag save(Tag entity) {
        return repository.save(entity);
    }

    @Override
    @Transactional
    public List<Tag> saveAll(List<Tag> entities) {
        return repository.saveAll(entities);
    }

    @Override
    public Optional<Tag> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<Tag> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void delete(Tag entity) {
        repository.delete(entity);
    }

    @Override
    public boolean existsById(String id) {
        return repository.existsById(id);
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public boolean existsByName(String name) {
        return repository.existsByName(name);
    }

    @Override
    @Transactional
    public Tag createTag(Tag tag) {
        if (existsByName(tag.getName())) {
            throw new IllegalArgumentException("Tag name already exists");
        }

        tag.setId(UUID.randomUUID().toString());
        tag.setCreatedAt(new Date());
        return save(tag);
    }

    @Override
    @Transactional
    public Tag updateTag(Tag tag) {
        Tag existingTag = findById(tag.getId())
            .orElseThrow(() -> new IllegalArgumentException("Tag not found"));

        if (!existingTag.getName().equals(tag.getName()) && existsByName(tag.getName())) {
            throw new IllegalArgumentException("Tag name already exists");
        }

        return save(tag);
    }
} 