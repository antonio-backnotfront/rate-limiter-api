package io.github.antonio.backnotfront.ratelimiter.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "Policy")
@Getter
@Setter
public class Policy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String endpointPattern = "/**";

    // maximum number of requests per window
    private Integer capacity = 100;

    // in seconds
    private Integer windowSize = 60;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;
}
