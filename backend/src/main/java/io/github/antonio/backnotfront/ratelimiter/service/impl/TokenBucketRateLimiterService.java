package io.github.antonio.backnotfront.ratelimiter.service.impl;

import io.github.antonio.backnotfront.ratelimiter.dto.response.GetPolicyResponseDto;
import io.github.antonio.backnotfront.ratelimiter.exception.NotFoundException;
import io.github.antonio.backnotfront.ratelimiter.model.RateLimitCacheEntry;
import io.github.antonio.backnotfront.ratelimiter.repository.RateLimiterRepository;
import io.github.antonio.backnotfront.ratelimiter.service.PolicyService;
import io.github.antonio.backnotfront.ratelimiter.service.RateLimiterService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Duration;

// TODO: refactor code for clarity and coherence
// TODO: refactor code for clarity and coherence
// TODO: refactor code for clarity and coherence
@Service
public class TokenBucketRateLimiterService implements RateLimiterService {


    private final RateLimiterRepository repository;
    private final PolicyService policyService;

    public TokenBucketRateLimiterService(RateLimiterRepository repository, PolicyService policyService) {
        this.repository = repository;
        this.policyService = policyService;
    }

    @Override
    public boolean isAllowed(String policyId, String user) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        GetPolicyResponseDto policy = policyService.getPolicyById(policyId);
        if (!policy.userEmail().equals(currentUserEmail))
            throw new NotFoundException(String.format("Policy with id %s not found.", policyId));

        String key = String.format("LIMITER::%s::%s", policyId, user);
        RateLimitCacheEntry cacheEntry = repository.getCacheEntry(key);
        double timeUnit = (double) policy.windowSize() / policy.capacity();

        if (cacheEntry == null || cacheEntry.getTokens() == null || cacheEntry.getLastRequest() == null) {
            repository.putCacheEntry(key, policy.capacity() - 1, Duration.ofSeconds(policy.windowSize()));
            if (timeUnit >= 1) {
                repository.updateLastRequest(key, ((System.currentTimeMillis() / 1000 / (long) timeUnit) * 1000 * (long) timeUnit));
            } else {
                repository.updateLastRequest(key, (long) ((System.currentTimeMillis() / 1000 / timeUnit) * 1000 * timeUnit));
            }
            return true;
        } else {
            Long timeDifference = (System.currentTimeMillis() - cacheEntry.getLastRequest()) / 1000;

            Long tokensToAdd = (long) (timeDifference / timeUnit);

            Integer newTokenCount = Math.toIntExact((tokensToAdd + cacheEntry.getTokens()) - 1);

            if (timeUnit >= 1) {
                repository.updateLastRequest(key, ((System.currentTimeMillis() / 1000 / (long) timeUnit) * 1000 * (long) timeUnit));
            } else {
                repository.updateLastRequest(key, (long) ((System.currentTimeMillis() / 1000 / timeUnit) * 1000 * timeUnit));
            }

            if (newTokenCount < 0) {
                repository.modifyCacheEntryExpiration(key, Duration.ofSeconds(policy.windowSize()));
                return false;
            } else if (newTokenCount <= policy.capacity()) {
                repository.incrementTokenBy(key, Math.toIntExact(tokensToAdd - 1));
            } else {
                repository.incrementTokenBy(key, policy.capacity() - 1);
            }


            repository.modifyCacheEntryExpiration(key, Duration.ofSeconds(policy.windowSize()));
            return true;

        }

    }
}
