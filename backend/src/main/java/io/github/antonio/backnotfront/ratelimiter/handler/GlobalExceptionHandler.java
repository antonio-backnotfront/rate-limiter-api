package io.github.antonio.backnotfront.ratelimiter.handler;

import io.github.antonio.backnotfront.ratelimiter.exception.ApplicationException;
import io.github.antonio.backnotfront.ratelimiter.utility.ErrorBodyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<?> handleException(ApplicationException exception) {
        Map<String, Object> body = ErrorBodyFactory.generateBody(exception.getMessage(), exception.getStatusCode());
        return new ResponseEntity<>(body, HttpStatus.valueOf(exception.getStatusCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception exception) {
        logger.error(Arrays.toString(exception.getStackTrace()));
        Map<String, Object> body = ErrorBodyFactory.generateBody("Internal Server Error.", 500);
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
