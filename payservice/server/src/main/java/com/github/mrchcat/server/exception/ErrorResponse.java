package com.github.mrchcat.server.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ErrorResponse(@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss") LocalDateTime timestamp, Integer status, String message) {
    public ErrorResponse(Integer status, String message) {
        this(LocalDateTime.now(), status, message);
    }
}
