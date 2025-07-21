package io.github.antonio.backnotfront.ratelimiter.repository.impl;

import io.github.antonio.backnotfront.ratelimiter.repository.RateLimiterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisRateLimiterRepository implements RateLimiterRepository {

    @Autowired
    RedisTemplate<String, Integer> redisTemplate;

    @Override
    public Integer getTokenCount(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void incrementToken(String key) {
        redisTemplate.opsForValue().increment(key);
    }

    @Override
    public void decrementToken(String key) {
        redisTemplate.opsForValue().decrement(key);
    }
}
