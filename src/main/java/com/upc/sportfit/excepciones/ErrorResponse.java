package com.upc.sportfit.excepciones;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class ErrorResponse {
    private int statusCode;
    private String message;
    private List<String> errors;
    private Instant timestamp;

    public ErrorResponse(int statusCode, String message) {
        this(statusCode, message, null);
    }

    public ErrorResponse(int statusCode, String message, List<String> errors) {
        this.statusCode = statusCode;
        this.message = message;
        this.errors = errors;
        this.timestamp = Instant.now();
    }
}
