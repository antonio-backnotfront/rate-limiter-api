package io.github.antonio.backnotfront.ratelimiter.repository;

public interface RateLimiterRepository {
    Integer getTokenCount(String key);

    void incrementToken(String key);

    void decrementToken(String key);
}
