package io.github.antonio.backnotfront.ratelimiter.service.impl;

import io.github.antonio.backnotfront.ratelimiter.dto.request.CreatePolicyRequestDto;
import io.github.antonio.backnotfront.ratelimiter.dto.response.CreatePolicyResponseDto;
import io.github.antonio.backnotfront.ratelimiter.dto.response.GetPolicyResponseDto;
import io.github.antonio.backnotfront.ratelimiter.exception.NotFoundException;
import io.github.antonio.backnotfront.ratelimiter.model.Policy;
import io.github.antonio.backnotfront.ratelimiter.model.User;
import io.github.antonio.backnotfront.ratelimiter.repository.impl.PolicyRepository;
import io.github.antonio.backnotfront.ratelimiter.service.PolicyService;
import io.github.antonio.backnotfront.ratelimiter.service.UserService;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class PolicyServiceImpl implements PolicyService {

    private final PolicyRepository repository;
    private final UserService userService;
    private final CacheManager cacheManager;

    public PolicyServiceImpl(PolicyRepository repository, UserService userService, CacheManager cacheManager) {
        this.repository = repository;
        this.userService = userService;
        this.cacheManager = cacheManager;
    }

    @Override
    public GetPolicyResponseDto getPolicyById(String id) {
        Cache cache = cacheManager.getCache("POLICIES");
        Cache.ValueWrapper valueWrapper = cache.get(id);
        if (valueWrapper != null) {
            Policy policy = (Policy) valueWrapper.get();
            return new GetPolicyResponseDto(policy.getId(), policy.getEndpointPattern(), policy.getCapacity(), policy.getWindowSize(), policy.getUser().getEmail());
        }

        Optional<Policy> policyOptional = repository.findPolicyById(id);
        if (policyOptional.isEmpty())
            throw new NotFoundException(String.format("Policy with id %s not found.", id));
        Policy policy = policyOptional.get();
        cache.put(policy.getId(), policy);

        return new GetPolicyResponseDto(policy.getId(), policy.getEndpointPattern(), policy.getCapacity(), policy.getWindowSize(), policy.getUser().getEmail());
    }

    @Override
    public List<GetPolicyResponseDto> getPoliciesByUserEmail(String userEmail) {
        List<Policy> policies = repository.findPoliciesByUser_Email(userEmail);
        List<GetPolicyResponseDto> list = new LinkedList<>();
        for (Policy policy : policies) {
            list.add(new GetPolicyResponseDto(policy.getId(), policy.getEndpointPattern(), policy.getCapacity(), policy.getWindowSize(), policy.getUser().getEmail()));
        }
        return list;
    }

    @Override
    public CreatePolicyResponseDto createPolicy(CreatePolicyRequestDto requestDto) {
        Policy policy = new Policy();
        policy.setEndpointPattern(requestDto.endpointPattern());
        policy.setCapacity(requestDto.capacity());
        policy.setWindowSize(requestDto.windowSize());

        Optional<User> userOptional = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (userOptional.isEmpty())
            throw new RuntimeException();
        policy.setUser(userOptional.get());


        repository.save(policy);

        Cache cache = cacheManager.getCache("POLICIES");
        cache.put(policy.getId(), policy);

        return new CreatePolicyResponseDto(policy.getId(), policy.getEndpointPattern(), policy.getCapacity(), policy.getWindowSize(), policy.getUser().getEmail());
    }
}
