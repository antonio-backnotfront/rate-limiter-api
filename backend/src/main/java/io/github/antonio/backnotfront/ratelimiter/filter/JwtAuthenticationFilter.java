package io.github.antonio.backnotfront.ratelimiter.filter;

import io.github.antonio.backnotfront.ratelimiter.exception.UnauthorizedException;
import io.github.antonio.backnotfront.ratelimiter.model.enums.TokenTypeEnum;
import io.github.antonio.backnotfront.ratelimiter.utility.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    JwtService service;
    Logger logger;

    public JwtAuthenticationFilter(JwtService service) {
        this.service = service;
        logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        TokenTypeEnum tokenTypeEnum = null;
        UserDetails userDetails = null;
        String token = null;

        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7);
            try {
                tokenTypeEnum = service.extractTokenType(token);
                userDetails = service.extractUserDetails(token);
            } catch (UnauthorizedException e) {
                logger.warn("Cannot parse jwt: {}", e.getMessage());
            }
        }

        if (
                tokenTypeEnum != null && tokenTypeEnum.equals(TokenTypeEnum.ACCESS)
                        && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (service.isTokenValid(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);

    }
}
