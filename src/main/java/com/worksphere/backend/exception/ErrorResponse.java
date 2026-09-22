package com.worksphere.backend.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
public class ErrorResponse {

    private LocalDateTime timeStamp;
    private int status;
    private String message;
    private Map<String, String> fieldErrors;

    public ErrorResponse(int status, String message) {
        this.timeStamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
    }

    public ErrorResponse(int status, String message, Map<String, String> fieldErrors) {
        this.timeStamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
        this.fieldErrors = fieldErrors;
    }
}
