package io.github.antonio.backnotfront.ratelimiter.repository.impl;

import io.github.antonio.backnotfront.ratelimiter.model.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, String> {
    Optional<Policy> findPolicyById(String id);

    List<Policy> findPoliciesByUserId(Long userId);
}
