package com.github.mrchcat.dto;

import lombok.ToString;

import java.time.LocalDateTime;

@ToString
public class ErrorResponse {

    String timestamp;
    Integer status;
    String message;

    public ErrorResponse(Integer status, String message) {
        this.timestamp=LocalDateTime.now().toString();
        this.status = status;
        this.message = message;
    }
}
