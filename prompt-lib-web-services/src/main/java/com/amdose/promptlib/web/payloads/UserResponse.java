package com.amdose.promptlib.web.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String id;
    private String username;
    private String email;
    private String avatarUrl;
    private int promptsUsed;
    private int promptsSaved;
} 