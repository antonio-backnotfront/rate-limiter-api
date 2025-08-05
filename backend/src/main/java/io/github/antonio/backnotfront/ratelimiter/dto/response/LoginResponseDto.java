package io.github.antonio.backnotfront.ratelimiter.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LoginResponseDto(
        @JsonProperty(value = "access_token") String accessToken,
        @JsonProperty(value = "refresh_token") String refreshToken) {
}
