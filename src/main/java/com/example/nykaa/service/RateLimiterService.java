package com.example.nykaa.service;

import com.example.nykaa.model.UserRateLimit;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Component
@Service
public class RateLimiterService {

    private Map<String, UserRateLimit> userRateLimitMap;

    public RateLimiterService(){
        userRateLimitMap = new HashMap<>();
    }

    public void configureLimit(String userId, int limit, long window){
        userRateLimitMap.put(userId,new UserRateLimit(limit,window));
    }

    public boolean allowRequest(String userId){
        if(!userRateLimitMap.containsKey(userId)){
            throw new IllegalArgumentException("User limit not configured for this user");
        }
        UserRateLimit userRateLimit = userRateLimitMap.get(userId);
        return userRateLimit.allowRequest();
    }


}




