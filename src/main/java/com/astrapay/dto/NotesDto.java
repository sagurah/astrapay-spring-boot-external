package com.astrapay.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotesDto {
    private String notesId;
    private String title;
    private String description;
    private String content;
    private String createdAt;
    private String updatedAt;
}

