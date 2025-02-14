package com.astrapay.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Notes {
    private UUID uuid;
    private String title;
    private String description;
    private String content;
    private String createdAt = LocalDateTime.now().toString();
    private String updatedAt = LocalDateTime.now().toString();
}
