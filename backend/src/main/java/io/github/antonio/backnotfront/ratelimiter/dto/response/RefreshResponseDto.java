package io.github.antonio.backnotfront.ratelimiter.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RefreshResponseDto(@JsonProperty("access_token") String accessToken) {
}
