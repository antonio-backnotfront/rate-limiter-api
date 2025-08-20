package io.github.antonio.backnotfront.ratelimiter.controller;

import io.github.antonio.backnotfront.ratelimiter.exception.BadRequestException;
import io.github.antonio.backnotfront.ratelimiter.model.RateLimitResponseHeadersContent;
import io.github.antonio.backnotfront.ratelimiter.service.RateLimiterService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> check(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        String policyHeader = request.getHeader("X-POLICY-ID");
        String userHeader = request.getHeader("X-USER");
        if (policyHeader == null)
            throw new BadRequestException("Header 'X-POLICY-ID' is required and cannot be null.");
        if (userHeader == null)
            throw new BadRequestException("Header 'X-USER' is required and cannot be null.");
        RateLimitResponseHeadersContent responseHeadersContent =
                rateLimiterService.isAllowed(policyHeader, userHeader);

        HttpStatus statusCode = responseHeadersContent.getIsAllowed() ? HttpStatus.OK : HttpStatus.TOO_MANY_REQUESTS;
        response.setHeader("X-RateLimit-Limit", String.valueOf(responseHeadersContent.getLimit()));
        response.setHeader("X-RateLimit-Remaining", String.valueOf(responseHeadersContent.getRemaining()));
        response.setHeader("X-RateLimit-Reset", String.valueOf(responseHeadersContent.getReset()));

        return new ResponseEntity<>(statusCode);
    }
}


