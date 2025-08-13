package io.github.antonio.backnotfront.ratelimiter.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreatePolicyResponseDto(
        String id,
        @JsonProperty("endpoint_pattern") String endpointPattern,
        Integer capacity,
        @JsonProperty("window_size") Integer windowSize,
        @JsonProperty("user_email") String userEmail
) {
}
