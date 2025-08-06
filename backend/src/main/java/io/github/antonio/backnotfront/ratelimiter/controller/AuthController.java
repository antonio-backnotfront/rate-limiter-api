package io.github.antonio.backnotfront.ratelimiter.controller;

import io.github.antonio.backnotfront.ratelimiter.dto.request.LoginRequestDto;
import io.github.antonio.backnotfront.ratelimiter.dto.request.RegisterRequestDto;
import io.github.antonio.backnotfront.ratelimiter.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService service;

    public AuthController(UserService service) {
        this.service = service;
    }

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequestDto requestDto) {
        return new ResponseEntity<>(service.register(requestDto), HttpStatus.OK);

    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDto requestDto) {
        return new ResponseEntity<>(service.login(requestDto), HttpStatus.OK);
    }
}
