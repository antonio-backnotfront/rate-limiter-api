package io.github.antonio.backnotfront.ratelimiter.controller;

import io.github.antonio.backnotfront.ratelimiter.service.RateLimiterService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rate-limiter")
public class RateLimiterController {

    private final RateLimiterService rateLimiterService;

    public RateLimiterController(RateLimiterService rateLimiterService) {
        this.rateLimiterService = rateLimiterService;
    }

    @PostMapping("check")
    public ResponseEntity<?> check(HttpServletRequest request) throws InterruptedException {
        String policyHeader = request.getHeader("X-POLICY-ID");
        String userHeader = request.getHeader("X-USER");
        return new ResponseEntity<>(rateLimiterService.isAllowed(policyHeader, userHeader), HttpStatus.OK);
    }
}


