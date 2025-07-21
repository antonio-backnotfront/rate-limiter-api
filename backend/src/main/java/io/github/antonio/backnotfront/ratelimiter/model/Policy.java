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
    private Integer capacity;
    private Long windowSize;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;
}
