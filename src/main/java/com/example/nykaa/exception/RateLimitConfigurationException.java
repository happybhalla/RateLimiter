package com.example.nykaa.exception;

public class RateLimitConfigurationException extends RuntimeException {
    public RateLimitConfigurationException(String message) {
        super(message);
    }
}
