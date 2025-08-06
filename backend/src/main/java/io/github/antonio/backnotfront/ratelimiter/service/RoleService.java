package io.github.antonio.backnotfront.ratelimiter.service;

import io.github.antonio.backnotfront.ratelimiter.model.Role;
import io.github.antonio.backnotfront.ratelimiter.model.enums.RoleEnum;

public interface RoleService {
    Role getRoleByRoleEnum(RoleEnum roleEnum);
}
