package io.github.antonio.backnotfront.ratelimiter.service;

import io.github.antonio.backnotfront.ratelimiter.dto.request.LoginRequestDto;
import io.github.antonio.backnotfront.ratelimiter.dto.request.RegisterRequestDto;
import io.github.antonio.backnotfront.ratelimiter.dto.response.LoginResponseDto;
import io.github.antonio.backnotfront.ratelimiter.dto.response.RefreshResponseDto;
import io.github.antonio.backnotfront.ratelimiter.dto.response.RegisterResponseDto;
import io.github.antonio.backnotfront.ratelimiter.model.Policy;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface PolicyService {
    Policy getPolicyById(Long id);
    List<Policy> getPoliciesByUserId(Long userId);
    Policy createPolicy(Long userId);
}
