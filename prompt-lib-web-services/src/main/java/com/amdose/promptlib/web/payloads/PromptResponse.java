package com.amdose.promptlib.web.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromptResponse {
    private String id;
    private String title;
    private String content;
    private String previewText;
    private double rating;
    private int usesCount;
    private String createdBy;
    private boolean isFeatured;
    private boolean isTrending;
    private boolean isNew;
    private String status;
    private List<String> categories;
    private List<String> tags;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 