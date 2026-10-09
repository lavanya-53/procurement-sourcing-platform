package com.spo.core_app.Security;

import com.spo.core_app.models.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.io.IOException;
import java.util.Collections;

// This class acts as a JWT filter
@Component
public class JwtFilter extends OncePerRequestFilter {

    private com.spo.core_app.Utilities.JWTutility jwtutility;

    @Autowired
    public JwtFilter(com.spo.core_app.Utilities.JWTutility jwtutility) {
        this.jwtutility = jwtutility;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // Get the token from the request header
        String token = request.getHeader("token");

        // If there is no token, continue the request
        if (token == null || token.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        // Remove extra spaces
        token = token.trim();

        // Validate the token
        User user = jwtutility.ValidateToken(token);

        // If token is invalid, continue without authentication
        if (user == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // Tell Spring Security which user is logged in
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        Collections.emptyList()
                );

        SecurityContextHolder.getContext()
                .setAuthentication(usernamePasswordAuthenticationToken);

        filterChain.doFilter(request, response);
    }
}