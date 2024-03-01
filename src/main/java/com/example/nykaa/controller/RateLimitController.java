package com.example.nykaa.controller;

import com.example.nykaa.model.RateLimit;
import com.example.nykaa.service.RateLimiterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RateLimitController {
    private static final Logger logger = LoggerFactory.getLogger(RateLimitController.class);

    private final RateLimiterService rateLimiter;

    @Autowired
    public RateLimitController(RateLimiterService rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    @PostMapping("/configure/{userId}")
    public ResponseEntity<String> configure(@PathVariable String userId,
                                            @RequestBody RateLimit rateLimit) {
        logger.info("user id {}",userId);
        rateLimiter.configureLimit(userId,rateLimit.getLimit(),rateLimit.getWindow());
        return ResponseEntity.ok("Rate limit configured successfully for user: " + userId);
    }

    @GetMapping("/request/{userId}")
    public ResponseEntity<String> request(@PathVariable String userId) {
        boolean allowed = rateLimiter.allowRequest(userId);
        if (allowed) {
            return ResponseEntity.ok("Request allowed for user: " + userId);
        } else {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Request blocked for user: " + userId);
        }
    }

}