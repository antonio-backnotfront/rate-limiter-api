package io.github.antonio.backnotfront.ratelimiter.service;

import io.github.antonio.backnotfront.ratelimiter.dto.request.LoginRequestDto;
import io.github.antonio.backnotfront.ratelimiter.dto.request.RegisterRequestDto;
import io.github.antonio.backnotfront.ratelimiter.dto.response.LoginResponseDto;
import io.github.antonio.backnotfront.ratelimiter.dto.response.RefreshResponseDto;
import io.github.antonio.backnotfront.ratelimiter.dto.response.RegisterResponseDto;
import io.github.antonio.backnotfront.ratelimiter.model.User;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public interface UserService {
    LoginResponseDto login(LoginRequestDto requestDto);
    RegisterResponseDto register(RegisterRequestDto requestDto);
    RefreshResponseDto refresh(HttpServletRequest request);
    Optional<User> getUserByEmail(String email);
}
