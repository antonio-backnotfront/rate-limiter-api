package io.github.antonio.backnotfront.ratelimiter.service;

import io.github.antonio.backnotfront.ratelimiter.model.RateLimitResponseHeadersContent;

public interface RateLimiterService {
    RateLimitResponseHeadersContent isAllowed(String policyId, String user);
}
