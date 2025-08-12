package io.github.antonio.backnotfront.ratelimiter.repository.impl;

import io.github.antonio.backnotfront.ratelimiter.repository.RateLimiterRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
public class RedisRateLimiterRepository implements RateLimiterRepository {

    private final RedisTemplate<String, Integer> redisTemplate;

    public RedisRateLimiterRepository(RedisTemplate<String, Integer> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Integer getTokenCount(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void putTokenCountIfAbsent(String key, Integer value, Duration duration) {
        redisTemplate.opsForValue().setIfAbsent(key, value, duration);
    }

    @Override
    public void modifyTokenExpiration(String key, Duration duration) {
        redisTemplate.expire(key, duration);
    }

    @Override
    public void incrementToken(String key) {
        redisTemplate.opsForValue().increment(key);
    }

    @Override
    public void incrementTokenBy(String key, Integer value) {
        redisTemplate.opsForValue().increment(key, value);
    }

    @Override
    public void decrementToken(String key) {
        redisTemplate.opsForValue().decrement(key);
    }

    @Override
    public void decrementTokenBy(String key, Integer value) {
        redisTemplate.opsForValue().decrement(key, value);
    }
}
