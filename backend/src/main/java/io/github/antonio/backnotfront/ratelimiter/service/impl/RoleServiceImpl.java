package io.github.antonio.backnotfront.ratelimiter.service.impl;

import io.github.antonio.backnotfront.ratelimiter.model.Role;
import io.github.antonio.backnotfront.ratelimiter.model.enums.RoleEnum;
import io.github.antonio.backnotfront.ratelimiter.repository.impl.RoleRepository;
import io.github.antonio.backnotfront.ratelimiter.service.RoleService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    RoleRepository repository;

    public RoleServiceImpl(RoleRepository repository) {
        this.repository = repository;
    }

    @Override
    @Cacheable(value = "ROLES", key = "#roleEnum")
    public Role getRoleByRoleEnum(RoleEnum roleEnum) {
        Optional<Role> roleOptional =  repository.findByName(roleEnum);
        if (roleOptional.isEmpty())
            throw new RuntimeException(String.format("Role with name '%s' not found.", roleEnum.name()));
        return roleOptional.get();
    }
}
