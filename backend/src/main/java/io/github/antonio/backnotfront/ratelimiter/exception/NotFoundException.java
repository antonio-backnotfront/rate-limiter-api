package io.github.antonio.backnotfront.ratelimiter.exception;

public class NotFoundException extends ApplicationException {
    public NotFoundException(String msg) {
        super(msg, 404);
    }
}
