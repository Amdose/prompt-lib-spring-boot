package com.amdose.promptlib.database.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "tags")
public class Tag {
    @Id
    private String id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;
} 