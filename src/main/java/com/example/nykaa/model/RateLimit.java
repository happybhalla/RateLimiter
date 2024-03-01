package com.example.nykaa.model;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RateLimit {
    @Min(value = 0, message = "Limit must be non-negative")
    private int limit;

    @Min(value = 0, message = "Window must be non-negative")
    private long window;
}
