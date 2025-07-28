package io.github.antonio.backnotfront.ratelimiter.service.impl;

import io.github.antonio.backnotfront.ratelimiter.repository.impl.RedisRateLimiterRepository;
import io.github.antonio.backnotfront.ratelimiter.service.RateLimiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RateLimiterServiceImpl implements RateLimiterService {


    @Autowired
    RedisRateLimiterRepository repository;

    @Override
    public boolean isAllowed(Long policyId, String userId) {
        return false;
    }
}
