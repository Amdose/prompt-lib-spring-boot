package com.amdose.promptlib.web.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {
    private String id;
    private String name;
    private int displayOrder;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public CategoryResponse(String id, String name, int displayOrder) {
        this.id = id;
        this.name = name;
        this.displayOrder = displayOrder;
        this.createdAt = LocalDateTime.now().minusDays(30);
        this.updatedAt = LocalDateTime.now();
    }
} 