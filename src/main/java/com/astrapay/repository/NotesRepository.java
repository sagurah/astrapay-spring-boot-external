package com.astrapay.repository;

import com.astrapay.entity.Notes;

import java.util.*;

public class NotesRepository {
    private final Map<UUID, Notes> notesStore = new HashMap<>();

    public Notes save(Notes notes) {
        notesStore.put(notes.getUuid(), notes);
        return notes;
    }

    public List<Notes> findAll() {
        return List.copyOf(notesStore.values());
    }

    public Optional<Notes> findByUuid(UUID uuid) {
        return Optional.ofNullable(notesStore.get(uuid));
    }

    public Notes update(UUID uuid, Notes notes) {
        Notes existingEntity = notesStore.get(uuid);

        existingEntity.setTitle(notes.getTitle());
        existingEntity.setDescription(notes.getDescription());
        existingEntity.setContent(notes.getContent());
        existingEntity.setUpdatedAt(notes.getUpdatedAt());

        return notesStore.put(uuid, existingEntity);
    }

    public Notes delete(UUID uuid) {
        return notesStore.remove(uuid);
    }
}
