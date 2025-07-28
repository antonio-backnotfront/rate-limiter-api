package io.github.antonio.backnotfront.ratelimiter.repository.impl;

import io.github.antonio.backnotfront.ratelimiter.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByNameIgnoreCase(String name);
}
