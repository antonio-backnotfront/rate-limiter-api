package io.github.antonio.backnotfront.ratelimiter.repository;

public interface RateLimiterRepository {
    Integer getTokenCount(String key);

    void incrementToken(String key);

    void decrementToken(String key);

    void incrementTokenBy(String key, Integer value);

    void decrementTokenBy(String key, Integer value);
}
