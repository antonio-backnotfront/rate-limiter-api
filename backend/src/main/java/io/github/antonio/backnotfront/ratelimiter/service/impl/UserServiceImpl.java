package io.github.antonio.backnotfront.ratelimiter.service.impl;

import io.github.antonio.backnotfront.ratelimiter.dto.request.LoginRequestDto;
import io.github.antonio.backnotfront.ratelimiter.dto.request.RegisterRequestDto;
import io.github.antonio.backnotfront.ratelimiter.dto.response.LoginResponseDto;
import io.github.antonio.backnotfront.ratelimiter.dto.response.RegisterResponseDto;
import io.github.antonio.backnotfront.ratelimiter.exception.UnauthorizedException;
import io.github.antonio.backnotfront.ratelimiter.model.User;
import io.github.antonio.backnotfront.ratelimiter.repository.impl.UserRepository;
import io.github.antonio.backnotfront.ratelimiter.service.UserService;
import io.github.antonio.backnotfront.ratelimiter.utility.JwtService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService, UserDetailsService userDetailsService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public LoginResponseDto login(LoginRequestDto requestDto) {
        Optional<User> userOptional = repository.findByEmail(requestDto.email());
        if (userOptional.isEmpty()) rejectLoginRequest();
        User user = userOptional.get();
        if (!passwordEncoder.matches(requestDto.password(), user.getPassword())) {
            rejectLoginRequest();
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String accessToken = jwtService.generateAccessToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);
        return new LoginResponseDto(accessToken, refreshToken);
    }

    private void rejectLoginRequest() {
        throw new UnauthorizedException("User credentials are wrong.");
    }

    @Override
    public RegisterResponseDto login(RegisterRequestDto requestDto) {
        return null;
    }

}
