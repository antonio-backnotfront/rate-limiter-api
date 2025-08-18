package io.github.antonio.backnotfront.ratelimiter.repository;

import io.github.antonio.backnotfront.ratelimiter.model.RateLimitCacheEntry;

import java.time.Duration;

public interface RateLimiterRepository {
    RateLimitCacheEntry getCacheEntry(String key);

    void putCacheEntry(String key, Integer tokenCount, Duration duration);

    void modifyCacheEntryExpiration(String key, Duration duration);

    void incrementToken(String key);

    void decrementToken(String key);

    void incrementTokenBy(String key, Integer value);

    void decrementTokenBy(String key, Integer value);

    void updateLastRequest(String key, Long timestamp);
}
