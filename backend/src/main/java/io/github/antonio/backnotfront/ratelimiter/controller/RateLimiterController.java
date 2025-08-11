package io.github.antonio.backnotfront.ratelimiter.controller;

import io.github.antonio.backnotfront.ratelimiter.exception.BadRequestException;
import io.github.antonio.backnotfront.ratelimiter.service.RateLimiterService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rate-limiter")
public class RateLimiterController {

    private final RateLimiterService rateLimiterService;

    public RateLimiterController(RateLimiterService rateLimiterService) {
        this.rateLimiterService = rateLimiterService;
    }

    @GetMapping("check")
    public ResponseEntity<?> check(HttpServletRequest request) {
        String policyHeader = request.getHeader("X-POLICY-ID");
        String userHeader = request.getHeader("X-USER");
//        SecurityContextHolder.getContext().getAuthentication()
        try {
            return new ResponseEntity<>(rateLimiterService.isAllowed(Long.parseLong(policyHeader), userHeader), HttpStatus.OK);
        } catch (NumberFormatException e){
            throw new BadRequestException("X-POLICY-ID header must be a valid id of policy.");
        }
    }
}
