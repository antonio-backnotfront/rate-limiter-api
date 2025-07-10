package io.github.antonio.backnotfront.ratelimiter.exception;

public class BadRequestException extends ApplicationException {
    public BadRequestException(String msg) {
        super(msg, 400);
    }
}
