package com.astrapay.controller;

import com.astrapay.dto.BaseResponseDto;
import com.astrapay.dto.NotesDto;
import com.astrapay.dto.NotesIdDto;
import com.astrapay.dto.NotesRequestDto;
import com.astrapay.service.NotesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@CrossOrigin(originPatterns = "*")
@RestController
@Api(value = "Notes Controller")
@RequestMapping("/notes")
@Slf4j
public class NotesController {
    private final NotesService notesService;

    public NotesController(NotesService notesService) {
        this.notesService = notesService;
    }

    @GetMapping("/list")
    @ApiOperation(value = "Get all notes")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK", response = NotesDto.class, responseContainer = "List")
            }
    )
    public ResponseEntity<BaseResponseDto<List<NotesDto>>> getAllNotes() {
        log.info("Incoming get all notes request");

        List<NotesDto> resListNotesDto = notesService.getAllNotes();

        return ResponseEntity.ok(
                new BaseResponseDto<>(
                        HttpStatus.OK.value(),
                        "Sucessfully get all notes",
                        resListNotesDto
                )
        );
    }

    @ApiOperation(value = "Create Notes")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK", response = NotesDto.class)
            }
    )
    @PostMapping("/create")
    public ResponseEntity<BaseResponseDto<NotesDto>> createNotes(@Valid @RequestBody NotesRequestDto reqNotesDto) {
        log.info("Incoming create notes request");

        NotesDto resNotesDto = notesService.createNotes(reqNotesDto);

        return ResponseEntity.ok(
                new BaseResponseDto<>(
                        HttpStatus.CREATED.value(),
                        "Sucessfully created notes",
                        resNotesDto
                )
        );
    }

    @ApiOperation(value = "Get Notes by Id")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK", response = NotesDto.class)
            }
    )
    @GetMapping("/get/{notesId}")
    public ResponseEntity<BaseResponseDto<NotesDto>> getNotesById(@PathVariable String notesId) {
        log.info("Incoming get notes by id request");

        NotesDto resGetNotesDto = notesService.getNotesById(UUID.fromString(notesId));

        return ResponseEntity.ok(
                new BaseResponseDto<>(
                        HttpStatus.OK.value(),
                        "Sucessfully get notes by ID: " + notesId,
                        resGetNotesDto
                )
        );
    }

    @ApiOperation(value = "Update Notes")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK", response = NotesDto.class)
            }
    )
    @PutMapping("/update/{notesId}")
    public ResponseEntity<BaseResponseDto<NotesDto>> updateNotes(@Valid @RequestBody NotesRequestDto reqNotesDto, @PathVariable String notesId) {
        log.info("Incoming update notes request");

        NotesDto resNotesDto = notesService.updateNotes(reqNotesDto, UUID.fromString(notesId));

        return ResponseEntity.ok(
                new BaseResponseDto<>(
                        HttpStatus.OK.value(),
                        "Sucessfully updated notes",
                        resNotesDto
                )
        );
    }

    @ApiOperation(value = "Delete Notes")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK", response = NotesDto.class)
            }
    )
    @DeleteMapping("/delete/{notesId}")
    public ResponseEntity<BaseResponseDto<NotesIdDto>> deleteNotes(@PathVariable String notesId) {
        log.info("Incoming delete notes request");

        NotesIdDto resNotesDto = notesService.deleteNotes(UUID.fromString(notesId));

        return ResponseEntity.ok(
                new BaseResponseDto<>(
                        HttpStatus.OK.value(),
                        "Sucessfully deleted notes",
                        resNotesDto
                )
        );
    }
}
