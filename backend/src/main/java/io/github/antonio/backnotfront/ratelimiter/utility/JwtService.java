package io.github.antonio.backnotfront.ratelimiter.utility;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtService {
    @Value("${jwt.secret}")
    private String secretKey;

    public String generateToken(UserDetails userDetails) throws Exception {
        throw new Exception("Not implemented.");
    }
}
