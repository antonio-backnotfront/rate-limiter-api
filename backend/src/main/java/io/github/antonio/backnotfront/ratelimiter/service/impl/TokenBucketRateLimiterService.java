package io.github.antonio.backnotfront.ratelimiter.service.impl;

import io.github.antonio.backnotfront.ratelimiter.repository.RateLimiterRepository;
import io.github.antonio.backnotfront.ratelimiter.service.RateLimiterService;
import org.springframework.stereotype.Service;

@Service
public class TokenBucketRateLimiterService implements RateLimiterService {


    private final RateLimiterRepository repository;

    public TokenBucketRateLimiterService(RateLimiterRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean isAllowed(Long policyId, String userId) {
        return false;
    }
}
