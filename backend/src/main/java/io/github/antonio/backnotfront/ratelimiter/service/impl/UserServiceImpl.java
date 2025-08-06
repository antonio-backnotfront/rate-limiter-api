package io.github.antonio.backnotfront.ratelimiter.service.impl;

import io.github.antonio.backnotfront.ratelimiter.dto.request.LoginRequestDto;
import io.github.antonio.backnotfront.ratelimiter.dto.request.RegisterRequestDto;
import io.github.antonio.backnotfront.ratelimiter.dto.response.LoginResponseDto;
import io.github.antonio.backnotfront.ratelimiter.dto.response.RegisterResponseDto;
import io.github.antonio.backnotfront.ratelimiter.exception.ConflictException;
import io.github.antonio.backnotfront.ratelimiter.exception.UnauthorizedException;
import io.github.antonio.backnotfront.ratelimiter.model.Role;
import io.github.antonio.backnotfront.ratelimiter.model.User;
import io.github.antonio.backnotfront.ratelimiter.model.UserPrincipal;
import io.github.antonio.backnotfront.ratelimiter.model.enums.RoleEnum;
import io.github.antonio.backnotfront.ratelimiter.repository.impl.UserRepository;
import io.github.antonio.backnotfront.ratelimiter.service.RoleService;
import io.github.antonio.backnotfront.ratelimiter.service.UserService;
import io.github.antonio.backnotfront.ratelimiter.utility.JwtService;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RoleService roleService;
    private final CacheManager cacheManager;

    public UserServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            CacheManager cacheManager,
            RoleService roleService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.roleService = roleService;
        this.cacheManager = cacheManager;
    }

    @Override
    @Cacheable(value = "LOGIN_RESPONSES", key = "#requestDto")
    public LoginResponseDto login(LoginRequestDto requestDto) {
        Optional<User> userOptional = getUserByEmail(requestDto.email());
        if (userOptional.isEmpty()) rejectLoginRequest();
        User foundUser = userOptional.get();
        if (!passwordEncoder.matches(requestDto.password(), foundUser.getPassword())) {
            rejectLoginRequest();
        }
        UserDetails userDetails = new UserPrincipal(foundUser);
        String accessToken = jwtService.generateAccessToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);
        return new LoginResponseDto(accessToken, refreshToken);
    }

    private void rejectLoginRequest() {
        throw new UnauthorizedException("User credentials are wrong.");
    }

    @Override
    public RegisterResponseDto register(RegisterRequestDto requestDto) {
        Optional<User> foundUser = getUserByEmail(requestDto.email());
        if (foundUser.isPresent()) {
            rejectRegisterRequest();
        }

        User user = new User();
        user.setEmail(requestDto.email());
        user.setPassword(passwordEncoder.encode(requestDto.password()));
        Role userRole = roleService.getRoleByRoleEnum(RoleEnum.ROLE_USER);
        user.setRole(userRole);

        User createdUser = userRepository.save(user);
        return new RegisterResponseDto(createdUser.getId(), createdUser.getEmail());
    }

    private void rejectRegisterRequest() {
        throw new ConflictException("Such user already exists.");
    }

    public Optional<User> getUserByEmail(String email) {
        Cache userCache = cacheManager.getCache("USERS");
        Cache.ValueWrapper cachedUser = userCache.get(email);
        if (cachedUser != null) {
            return Optional.ofNullable((User) cachedUser.get());
        }
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) userCache.put(email, userOptional.get());
        return userOptional;
    }

}
