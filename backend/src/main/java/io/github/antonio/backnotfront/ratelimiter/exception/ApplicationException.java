package io.github.antonio.backnotfront.ratelimiter.exception;

import lombok.Getter;

@Getter
public class ApplicationException extends Exception {
    private final int statusCode;
    public ApplicationException(String msg, int statusCode) {
        super(msg);
        this.statusCode = statusCode;
    }
}
