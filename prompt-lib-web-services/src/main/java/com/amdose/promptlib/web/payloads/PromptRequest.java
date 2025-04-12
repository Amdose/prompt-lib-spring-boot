package com.amdose.promptlib.web.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class PromptRequest {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Content is required")
    private String content;

    private String previewText;

    @NotNull(message = "Categories are required")
    private List<String> categories;

    @NotNull(message = "Tags are required")
    private List<String> tags;
} 