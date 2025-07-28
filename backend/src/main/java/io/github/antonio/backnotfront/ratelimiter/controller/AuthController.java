package io.github.antonio.backnotfront.ratelimiter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


    @GetMapping("signup")
    public ResponseEntity<?> signup() {

        return new ResponseEntity<>("not implemented yet", HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping("signin")
    public ResponseEntity<?> signin() {
        return new ResponseEntity<>("not implemented yet", HttpStatus.NOT_IMPLEMENTED);
    }
}
