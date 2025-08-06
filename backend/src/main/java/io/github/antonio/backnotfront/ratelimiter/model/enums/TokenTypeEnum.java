package io.github.antonio.backnotfront.ratelimiter.model.enums;

import io.jsonwebtoken.JwtException;

public enum TokenTypeEnum {
    ACCESS, REFRESH;

    public static TokenTypeEnum fromString(String name) {
        try {
            return TokenTypeEnum.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new JwtException("Invalid token type: " + name);
        }
    }
}
