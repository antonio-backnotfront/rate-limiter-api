package io.github.antonio.backnotfront.ratelimiter.service;

import io.github.antonio.backnotfront.ratelimiter.model.Policy;

import java.util.Set;

public interface RateLimiterService {
    boolean isAllowed(Long policyId, String userId);
}
