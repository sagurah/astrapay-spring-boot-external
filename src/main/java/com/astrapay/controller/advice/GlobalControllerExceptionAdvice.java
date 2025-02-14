package com.astrapay.controller.advice;

import com.astrapay.dto.BaseResponseDto;
import com.astrapay.exception.DataNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
class GlobalControllerExceptionAdvice {
    private static final String BAD_REQUEST_MESSAGE = "Invalid Request";

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponseDto<?>> handleBadRequestException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        log.error("Invalid Request: {}", errors);

        return ResponseEntity.badRequest().body(
                new BaseResponseDto<>(
                        HttpStatus.BAD_REQUEST.value(),
                        BAD_REQUEST_MESSAGE,
                        errors
                )
        );
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<BaseResponseDto<?>> handleDataNotFoundException(DataNotFoundException e) {
        log.error("Data not found", e);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new BaseResponseDto<>(
                        HttpStatus.NOT_FOUND.value(),
                        e.getMessage(),
                        null
                )
        );
    }
}
