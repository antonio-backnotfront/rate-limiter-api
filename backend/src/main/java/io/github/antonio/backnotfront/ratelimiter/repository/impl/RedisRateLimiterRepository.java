package io.github.antonio.backnotfront.ratelimiter.repository.impl;

import io.github.antonio.backnotfront.ratelimiter.model.RateLimitCacheEntry;
import io.github.antonio.backnotfront.ratelimiter.repository.RateLimiterRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
public class RedisRateLimiterRepository implements RateLimiterRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    private final String TOKEN_COUNT_KEY = "tokenCount";
    private final String LAST_REQUEST_KEY = "lastRequest";

    public RedisRateLimiterRepository(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public RateLimitCacheEntry getCacheEntry(String key) {
        Object tokenCountObj = redisTemplate.opsForHash().get(key, TOKEN_COUNT_KEY);
        Object lastRequestObj = redisTemplate.opsForHash().get(key, LAST_REQUEST_KEY);

        if (tokenCountObj == null && lastRequestObj == null) {
            return null;
        }

        Integer tokenCount = tokenCountObj != null ? Integer.valueOf(tokenCountObj.toString()) : null;
        Long lastRequest = lastRequestObj != null ? Long.valueOf(lastRequestObj.toString()) : null;

        return new RateLimitCacheEntry(tokenCount, lastRequest);
    }

    @Override
    public void putCacheEntry(String key, Integer tokenCount, Duration duration) {
        redisTemplate.opsForHash().put(key, TOKEN_COUNT_KEY, tokenCount);
        redisTemplate.expire(key, duration);
    }

    @Override
    public void modifyCacheEntryExpiration(String key, Duration duration) {
        redisTemplate.expire(key, duration);
    }

    @Override
    public void incrementToken(String key) {
        redisTemplate.opsForHash().increment(key, TOKEN_COUNT_KEY, 1);
    }

    @Override
    public void incrementTokenBy(String key, Integer value) {
        redisTemplate.opsForHash().increment(key, TOKEN_COUNT_KEY, value);
    }

    @Override
    public void decrementToken(String key) {
        redisTemplate.opsForHash().increment(key, TOKEN_COUNT_KEY, -1);
    }

    @Override
    public void decrementTokenBy(String key, Integer value) {
        redisTemplate.opsForHash().increment(key, TOKEN_COUNT_KEY, -value);
    }

    @Override
    public void updateLastRequest(String key, Long timestamp) {
        redisTemplate.opsForHash().put(key, LAST_REQUEST_KEY, timestamp);
    }
}
