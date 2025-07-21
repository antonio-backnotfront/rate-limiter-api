package io.github.antonio.backnotfront.ratelimiter.service.impl;

import io.github.antonio.backnotfront.ratelimiter.model.Policy;
import io.github.antonio.backnotfront.ratelimiter.repository.impl.RedisRateLimiterRepository;
import io.github.antonio.backnotfront.ratelimiter.service.RateLimiterService;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RateLimiterServiceImpl implements RateLimiterService {


    @Autowired
    RedisRateLimiterRepository repository;

    @Override
    public boolean isAllowed(Long policyId, String userId) {
        return false;
    }
}
