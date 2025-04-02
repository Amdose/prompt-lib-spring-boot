package com.amdose.promptlib.web.controllers;

import com.amdose.promptlib.database.entities.Tag;
import com.amdose.promptlib.web.services.TagService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    public Tag createTag(@Valid @RequestBody Tag tag) {
        return tagService.createTag(tag);
    }

    @GetMapping("/{id}")
    public Tag getTagById(@PathVariable String id) {
        return tagService.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Tag not found"));
    }

    @GetMapping
    public List<Tag> getAllTags() {
        return tagService.findAll();
    }

    @PutMapping("/{id}")
    public Tag updateTag(@PathVariable String id, @Valid @RequestBody Tag tag) {
        tag.setId(id);
        return tagService.updateTag(tag);
    }

    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable String id) {
        tagService.deleteById(id);
    }

    @GetMapping("/name/{name}")
    public Tag getTagByName(@PathVariable String name) {
        return tagService.findByName(name)
            .orElseThrow(() -> new IllegalArgumentException("Tag not found"));
    }
} 