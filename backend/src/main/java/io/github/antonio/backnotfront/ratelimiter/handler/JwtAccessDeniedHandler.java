package io.github.antonio.backnotfront.ratelimiter.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.antonio.backnotfront.ratelimiter.utility.ErrorBodyFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");

        String body =
                new ObjectMapper()
                        .writeValueAsString(
                                ErrorBodyFactory.generateBody("Forbidden: access denied.", 403)
                        );
        response.getWriter().write(body);
    }
}
