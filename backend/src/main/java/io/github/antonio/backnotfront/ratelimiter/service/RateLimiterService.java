package io.github.antonio.backnotfront.ratelimiter.service;

public interface RateLimiterService {
    boolean isAllowed(String policyId, String user);
}
