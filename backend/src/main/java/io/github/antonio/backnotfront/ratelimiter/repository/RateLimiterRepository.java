package io.github.antonio.backnotfront.ratelimiter.repository;

import java.time.Duration;

public interface RateLimiterRepository {
    Integer getTokenCount(String key);

    void putTokenCountIfAbsent(String key, Integer value, Duration duration);

    void modifyTokenExpiration(String key, Duration duration);

    void incrementToken(String key);

    void decrementToken(String key);

    void incrementTokenBy(String key, Integer value);

    void decrementTokenBy(String key, Integer value);
}
