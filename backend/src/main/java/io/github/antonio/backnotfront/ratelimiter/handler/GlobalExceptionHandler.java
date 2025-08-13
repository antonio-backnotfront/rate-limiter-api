package io.github.antonio.backnotfront.ratelimiter.handler;

import io.github.antonio.backnotfront.ratelimiter.exception.ApplicationException;
import io.github.antonio.backnotfront.ratelimiter.utility.ErrorBodyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleException(MethodArgumentNotValidException exception) {
        Map<String, String> errorBody = new HashMap<>();
        for (FieldError error : exception.getFieldErrors()) {
            errorBody.put(error.getField(), error.getDefaultMessage());
        }
        Map<String, Object> body = ErrorBodyFactory.generateBody(errorBody, 400);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleException(HttpMessageNotReadableException exception) {
        Map<String, Object> body = ErrorBodyFactory.generateBody("Request body is required.", 400);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

}
