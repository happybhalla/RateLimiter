package com.example.nykaa;

import com.example.nykaa.controller.RateLimitController;
import com.example.nykaa.model.RateLimit;
import com.example.nykaa.service.RateLimiterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RateLimitControllerTest {

    @Mock
    private RateLimiterService rateLimiter;

    @InjectMocks
    private RateLimitController rateLimitController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testConfigure_Success() {
        RateLimit rateLimit = new RateLimit(10, 10000);
        ResponseEntity<String> responseEntity = rateLimitController.configure("user1", rateLimit);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Rate limit configured successfully for user: user1", responseEntity.getBody());
        verify(rateLimiter, times(1)).configureLimit("user1", 10, 10000);
    }

    @Test
    public void testRequest_Success() {
        when(rateLimiter.allowRequest("user1")).thenReturn(true);

        ResponseEntity<String> responseEntity = rateLimitController.request("user1");
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Request allowed for user: user1", responseEntity.getBody());
        verify(rateLimiter, times(1)).allowRequest("user1");
    }

    @Test
    public void testRequest_TooManyRequests() {
        when(rateLimiter.allowRequest("user1")).thenReturn(false);

        ResponseEntity<String> responseEntity = rateLimitController.request("user1");
        assertEquals(HttpStatus.TOO_MANY_REQUESTS, responseEntity.getStatusCode());
        assertEquals("Request blocked for user: user1", responseEntity.getBody());
        verify(rateLimiter, times(1)).allowRequest("user1");
    }
}
