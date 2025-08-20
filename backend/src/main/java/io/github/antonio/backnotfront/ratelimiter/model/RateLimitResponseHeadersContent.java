package io.github.antonio.backnotfront.ratelimiter.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RateLimitResponseHeadersContent {
    private Integer limit;
    private Integer remaining;
    private Long reset;
    private Boolean isAllowed;
}
