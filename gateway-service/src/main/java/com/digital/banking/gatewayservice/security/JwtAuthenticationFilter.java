package com.digital.banking.gatewayservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Component
public class JwtAuthenticationFilter implements WebFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getPath().toString();
        System.out.println("=== JWT Filter Debug ===");
        System.out.println("Processing path: " + path);

        // Allow unauthenticated requests to auth-service
        if (path.startsWith("/api/auth/") || path.startsWith("/api/user/")) {
            System.out.println("Allowing unauthenticated access to: " + path);
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        System.out.println("Authorization header: " + authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("No valid Authorization header found - returning 401");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);
        System.out.println("Extracted token (first 20 chars): " + token.substring(0, Math.min(token.length(), 20)) + "...");

        boolean isValidToken = jwtUtil.validateToken(token);
        System.out.println("Token validation result: " + isValidToken);

        if (!isValidToken) {
            System.out.println("Invalid token - returning 401");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        System.out.println("Token is valid - proceeding to downstream service");
        System.out.println("Headers being forwarded: " + exchange.getRequest().getHeaders().keySet());
        System.out.println("=========================");

        return chain.filter(exchange);
    }
}