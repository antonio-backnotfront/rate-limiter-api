package io.github.antonio.backnotfront.ratelimiter.exception;

public class UnauthorizedException extends ApplicationException {
    public UnauthorizedException(String msg) {
        super(msg, 401);
    }
}
