package io.github.antonio.backnotfront.ratelimiter.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(
        @NotBlank String email,
        @NotBlank String password) {
}
