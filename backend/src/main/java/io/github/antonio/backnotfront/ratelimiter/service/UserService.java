package io.github.antonio.backnotfront.ratelimiter.service;

import io.github.antonio.backnotfront.ratelimiter.dto.request.LoginRequestDto;
import io.github.antonio.backnotfront.ratelimiter.dto.request.RegisterRequestDto;
import io.github.antonio.backnotfront.ratelimiter.dto.response.LoginResponseDto;
import io.github.antonio.backnotfront.ratelimiter.dto.response.RegisterResponseDto;

public interface UserService {
    LoginResponseDto login(LoginRequestDto requestDto);
    RegisterResponseDto login(RegisterRequestDto requestDto);
}
