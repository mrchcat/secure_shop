package com.github.mrchcat.dto;

import lombok.ToString;

import java.time.LocalDateTime;

@ToString
public class ErrorResponse {

    private final String timestamp;
    private final Integer status;
    private final String message;

    public ErrorResponse(Integer status, String message) {
        this.timestamp = LocalDateTime.now().toString();
        this.status = status;
        this.message = message;
    }
}
