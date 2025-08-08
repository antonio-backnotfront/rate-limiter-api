package io.github.antonio.backnotfront.ratelimiter.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.antonio.backnotfront.ratelimiter.utility.ErrorBodyFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPointHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException)
            throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        String body =
                new ObjectMapper()
                        .writeValueAsString(
                                ErrorBodyFactory.generateBody("Unauthorized: valid access token is required.", 401)
                        );
        response.getWriter().write(body);

    }
}
