package io.github.antonio.backnotfront.ratelimiter.model;

import io.github.antonio.backnotfront.ratelimiter.utility.Base62Generator;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity(name = "Policy")
@Getter
@Setter
public class Policy {
    @Id
    private String id;
    private String endpointPattern = "/**";

    public Policy() {
        id = Base62Generator.generate(10);
    }

    // maximum number of requests per window
    private Integer capacity = 100;

    // in seconds
    private Integer windowSize = 60;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private User user;
}
