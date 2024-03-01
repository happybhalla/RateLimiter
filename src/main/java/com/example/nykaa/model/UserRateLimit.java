package com.example.nykaa.model;

import java.util.PriorityQueue;


public class UserRateLimit {
    private int limit;
    private long window;
    private PriorityQueue<Long> requestTimes;

    public UserRateLimit(int limit, long window) {
        this.limit = limit;
        this.window = window;
        this.requestTimes = new PriorityQueue<>();
    }
    public synchronized boolean allowRequest() {
        long currentTime = System.currentTimeMillis();

        while (!requestTimes.isEmpty() && requestTimes.peek() + window <= currentTime) {
            requestTimes.poll();
        }

        if (requestTimes.size() < limit) {
            requestTimes.offer(currentTime);
            return true;
        }
        return false;
    }

}
