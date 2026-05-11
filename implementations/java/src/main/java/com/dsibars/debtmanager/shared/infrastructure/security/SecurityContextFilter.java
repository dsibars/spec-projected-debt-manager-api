package com.dsibars.debtmanager.shared.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class SecurityContextFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof JwtAuthenticationToken jwtAuthentication) {
            Jwt jwt = jwtAuthentication.getToken();
            String userId = jwt.getSubject(); // sub claim
            String tenantId = jwt.getClaimAsString("tid"); // tid claim

            if (userId != null && tenantId != null) {
                try {
                    SecurityContext.set(UUID.fromString(userId), UUID.fromString(tenantId));
                } catch (IllegalArgumentException e) {
                    // Invalid UUID format
                }
            }
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            SecurityContext.clear();
        }
    }
}
