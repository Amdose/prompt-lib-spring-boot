package com.amdose.promptlib.database.entities;

import lombok.Data;
import java.io.Serializable;

@Data
public class SavedPromptId implements Serializable {
    private String user;
    private String prompt;
} 