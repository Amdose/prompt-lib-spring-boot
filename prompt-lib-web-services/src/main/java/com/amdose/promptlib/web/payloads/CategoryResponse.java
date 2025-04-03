package com.amdose.promptlib.web.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {
    private String id;
    private String name;
    private int displayOrder;
    private Date createdAt;
    private Date updatedAt;

    public CategoryResponse(String id, String name, int displayOrder) {
        this.id = id;
        this.name = name;
        this.displayOrder = displayOrder;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }
} 