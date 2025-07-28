package io.github.antonio.backnotfront.ratelimiter.service;

public interface RateLimiterService {
    boolean isAllowed(Long policyId, String userId);
}
