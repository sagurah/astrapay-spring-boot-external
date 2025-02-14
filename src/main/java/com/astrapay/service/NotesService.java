package com.astrapay.service;

import com.astrapay.dto.NotesDto;
import com.astrapay.dto.NotesIdDto;
import com.astrapay.dto.NotesRequestDto;
import com.astrapay.entity.Notes;
import com.astrapay.exception.DataNotFoundException;
import com.astrapay.repository.NotesRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class NotesService {
    private final NotesRepository notesRepository = new NotesRepository();

    public List<NotesDto> getAllNotes() {
        log.info("Getting all notes...");
        List<Notes> notesEntities = notesRepository.findAll();

        log.info("Sucessfully get all notes");
        return notesEntities.stream()
                .map(notes -> new NotesDto(
                        notes.getUuid().toString(),
                        notes.getTitle(),
                        notes.getDescription(),
                        notes.getContent(),
                        notes.getCreatedAt(),
                        notes.getUpdatedAt()
                ))
                .collect(Collectors.toList());
    }

    public NotesDto getNotesById(UUID notesId) {
        log.info("Getting notes by id: {}", notesId);
        Optional<Notes> notesEntity = notesRepository.findByUuid(notesId);

        if (notesEntity.isEmpty()) {
            log.error("Notes not found");
            throw new DataNotFoundException("Notes not found with ID: " + notesId);
        }

        log.info("Sucessfully get notes by id: {}", notesId);
        return new NotesDto(
                notesEntity.get().getUuid().toString(),
                notesEntity.get().getTitle(),
                notesEntity.get().getDescription(),
                notesEntity.get().getContent(),
                notesEntity.get().getCreatedAt(),
                notesEntity.get().getUpdatedAt()
        );
    }

    public NotesDto createNotes(NotesRequestDto createNotesRequestDto) {
        log.info("Creating notes...");
        Notes notes = new Notes();
        notes.setUuid(UUID.randomUUID());
        notes.setTitle(createNotesRequestDto.getTitle());
        notes.setDescription(createNotesRequestDto.getDescription());
        notes.setContent(createNotesRequestDto.getContent());

        Notes savedNotes = notesRepository.save(notes);
        log.info("Sucessfully create notes : {}", savedNotes);

        return new NotesDto(
                savedNotes.getUuid().toString(),
                savedNotes.getTitle(),
                savedNotes.getDescription(),
                savedNotes.getContent(),
                savedNotes.getCreatedAt(),
                savedNotes.getUpdatedAt()
        );
    }

    public NotesDto updateNotes(NotesRequestDto updateNotesRequestDto, UUID notesId) {
        log.info("Getting notes by id: {}", notesId);
        Optional<Notes> currentNotesEntity = notesRepository.findByUuid(notesId);

        if (currentNotesEntity.isEmpty()) {
            log.error("Notes not found");
            throw new DataNotFoundException("Notes not found with ID: " + notesId);
        }

        log.info("Updating notes...");
        Notes updatedNotesEntity = new Notes();
        updatedNotesEntity.setUuid(currentNotesEntity.get().getUuid());
        updatedNotesEntity.setTitle(updateNotesRequestDto.getTitle());
        updatedNotesEntity.setDescription(updateNotesRequestDto.getDescription());
        updatedNotesEntity.setContent(updateNotesRequestDto.getContent());
        updatedNotesEntity.setCreatedAt(currentNotesEntity.get().getCreatedAt());
        updatedNotesEntity.setUpdatedAt(LocalDateTime.now().toString());

        Notes updatedNotes = notesRepository.update(currentNotesEntity.get().getUuid(), updatedNotesEntity);
        log.info("Sucessfully update notes : {}", updatedNotes);

        return new NotesDto(
                updatedNotes.getUuid().toString(),
                updatedNotes.getTitle(),
                updatedNotes.getDescription(),
                updatedNotes.getContent(),
                updatedNotes.getCreatedAt(),
                updatedNotes.getUpdatedAt()
        );
    }

    public NotesIdDto deleteNotes(UUID notesIdDto) {
        log.info("Getting notes by id: {}", notesIdDto);
        Optional<Notes> findNotesEntity = notesRepository.findByUuid(notesIdDto);

        if (findNotesEntity.isEmpty()) {
            log.error("Notes not found");
            throw new DataNotFoundException("Notes not found with ID: " + notesIdDto);
        }

        Notes deleteNotes = notesRepository.delete(notesIdDto);
        log.info("Sucessfully delete notes : {}", deleteNotes);

        return new NotesIdDto(deleteNotes.getUuid().toString());
    }
}
