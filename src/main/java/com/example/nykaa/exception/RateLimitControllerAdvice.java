package com.example.nykaa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@ControllerAdvice
public class RateLimitControllerAdvice {
    @ExceptionHandler(RateLimitConfigurationException.class)
    public ResponseEntity<String> handleRateLimitConfigurationException(RateLimitConfigurationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
