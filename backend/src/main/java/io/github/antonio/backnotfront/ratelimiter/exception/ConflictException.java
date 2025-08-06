package io.github.antonio.backnotfront.ratelimiter.exception;

public class ConflictException extends ApplicationException {
    public ConflictException(String msg) {
        super(msg, 409);
    }
}
