package io.github.antonio.backnotfront.ratelimiter.service.impl;

import io.github.antonio.backnotfront.ratelimiter.dto.response.GetPolicyResponseDto;
import io.github.antonio.backnotfront.ratelimiter.exception.BadRequestException;
import io.github.antonio.backnotfront.ratelimiter.exception.NotFoundException;
import io.github.antonio.backnotfront.ratelimiter.model.RateLimitCacheEntry;
import io.github.antonio.backnotfront.ratelimiter.model.RateLimitResponseHeadersContent;
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
    public RateLimitResponseHeadersContent isAllowed(String policyHeader, String userHeader) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        GetPolicyResponseDto policy = policyService.getPolicyById(policyHeader);
        if (!policy.userEmail().equals(currentUserEmail))
            throw new NotFoundException(String.format("Policy with id %s not found.", policyHeader));

        String key = String.format("LIMITER::%s::%s", policyHeader, userHeader);
        RateLimitCacheEntry cacheEntry = repository.getCacheEntry(key);
        double timeUnit = (double) policy.windowSize() / policy.capacity();

        RateLimitResponseHeadersContent content = new RateLimitResponseHeadersContent();
        content.setLimit(policy.capacity());
        if (cacheEntry == null || cacheEntry.getTokens() == null || cacheEntry.getLastRequest() == null) {
            Integer remainingTokens = policy.capacity() - 1;
            repository.putCacheEntry(key, remainingTokens, Duration.ofSeconds(policy.windowSize()));
            if (timeUnit >= 1) {
                repository.updateLastRequest(key, ((System.currentTimeMillis() / 1000 / (long) timeUnit) * 1000 * (long) timeUnit));
            } else {
                repository.updateLastRequest(key, (long) ((System.currentTimeMillis() / 1000 / timeUnit) * 1000 * timeUnit));
            }
            content.setIsAllowed(true);
            content.setRemaining(remainingTokens);
            content.setReset((long) ((policy.capacity() - remainingTokens)*timeUnit*1000));
            return content;
        } else {
            Long timeDifference = (System.currentTimeMillis() - cacheEntry.getLastRequest()) / 1000;

            Long tokensToAdd = (long) (timeDifference / timeUnit);

            Integer newTokenCount = Math.toIntExact((tokensToAdd + cacheEntry.getTokens()) - 1);

            if (timeUnit >= 1) {
                repository.updateLastRequest(key, ((System.currentTimeMillis() / 1000 / (long) timeUnit) * 1000 * (long) timeUnit));
            } else {
                repository.updateLastRequest(key, (long) ((System.currentTimeMillis() / 1000 / timeUnit) * 1000 * timeUnit));
            }

            content.setRemaining(newTokenCount < policy.capacity() ? newTokenCount : policy.capacity()-1);
            content.setReset((long) ((policy.capacity() - content.getRemaining())*timeUnit*1000));

            if (newTokenCount < 0) {
                repository.modifyCacheEntryExpiration(key, Duration.ofSeconds(policy.windowSize()));
                content.setIsAllowed(false);
                return content;
            } else if (newTokenCount <= policy.capacity()) {
                repository.incrementTokenBy(key, Math.toIntExact(tokensToAdd - 1));
            } else {
                repository.incrementTokenBy(key, policy.capacity() - 1);
            }


            repository.modifyCacheEntryExpiration(key, Duration.ofSeconds(policy.windowSize()));
            content.setIsAllowed(true);
            return content;

        }

    }
}
