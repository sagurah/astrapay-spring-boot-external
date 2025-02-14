package com.astrapay.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseResponseDto<T> {
    private int code;
    private String message;
    private T data;
}
