package io.github.antonio.backnotfront.ratelimiter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rate-limiter")
public class RateLimiterController {


    @GetMapping("check")
    public ResponseEntity<?> check() {
        return new ResponseEntity<>("not implemented yet", HttpStatus.NOT_IMPLEMENTED);
    }
}
