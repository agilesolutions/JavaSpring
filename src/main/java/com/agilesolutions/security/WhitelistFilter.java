package com.agilesolutions.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

//@Component
public class WhitelistFilter extends OncePerRequestFilter {

    private static final List<String> WHITELIST = List.of(
            "/healthCheck",
            "/actuator/**",
            "/public",
            "/swagger-ui.html"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        if (WHITELIST.stream().anyMatch(path::startsWith)) {
            // Skip Spring Security, allow immediately
            response.setStatus(HttpServletResponse.SC_OK);
            filterChain.doFilter(request, response);
            //response.getWriter().write("Whitelisted OK");
            return;
        }

        // Not whitelisted → continue security chain (Keycloak auth)
        filterChain.doFilter(request, response);
    }
}