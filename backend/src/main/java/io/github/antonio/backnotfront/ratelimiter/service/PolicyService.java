package io.github.antonio.backnotfront.ratelimiter.service;

import io.github.antonio.backnotfront.ratelimiter.dto.request.CreatePolicyRequestDto;
import io.github.antonio.backnotfront.ratelimiter.dto.response.CreatePolicyResponseDto;
import io.github.antonio.backnotfront.ratelimiter.dto.response.GetPolicyResponseDto;

import java.util.List;

public interface PolicyService {
    GetPolicyResponseDto getPolicyById(String id);
    List<GetPolicyResponseDto> getPoliciesByUserEmail(String userEmail);
    CreatePolicyResponseDto createPolicy(CreatePolicyRequestDto requestDto);
}
