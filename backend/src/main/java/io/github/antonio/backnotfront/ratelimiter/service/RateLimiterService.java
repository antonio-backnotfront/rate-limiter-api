package io.github.antonio.backnotfront.ratelimiter.service;

public interface RateLimiterService {
    boolean isAllowed(Long consumerId, String policyId, String user);
}
