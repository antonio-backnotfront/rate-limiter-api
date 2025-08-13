package io.github.antonio.backnotfront.ratelimiter.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@NotNull
public record CreatePolicyRequestDto(
        @NotBlank @JsonProperty(value = "endpoint_pattern") String endpointPattern,
        @Positive @NotNull Integer capacity,
        @Positive @NotNull @JsonProperty(value = "window_size") Integer windowSize
) {
}
