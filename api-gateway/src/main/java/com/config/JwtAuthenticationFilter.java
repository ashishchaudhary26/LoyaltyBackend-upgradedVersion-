package com.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements WebFilter {

    private final String secret = "mysecretkey12345mysecretkey12345";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        // ---------- CORS preflight ----------
        if ("OPTIONS".equalsIgnoreCase(exchange.getRequest().getMethodValue())) {
            addCors(exchange);
            exchange.getResponse().setStatusCode(HttpStatus.OK);
            return exchange.getResponse().setComplete();
        }

        // ---------- Allow WebSocket / SockJS handshake ----------
        String upgrade = exchange.getRequest().getHeaders().getFirst("Upgrade");

        if ((upgrade != null && upgrade.equalsIgnoreCase("websocket"))
                || path.startsWith("/ws-notifications")) {
            return chain.filter(exchange);
        }

        // ---------- Public routes ----------
        if (path.equals("/api/v1/auth/login")
                || path.equals("/api/v1/auth/register")
                || (path.startsWith("/api/v1/products") && !path.startsWith("/api/v1/admin/products"))) {
            return chain.filter(exchange);
        }

        // ---------- Authorization ----------
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            addCors(exchange);
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secret.getBytes())
                    .parseClaimsJws(token)
                    .getBody();

            String userId = claims.getSubject();
            String role = claims.get("role", String.class);

            // ---------- Role check ----------
            if (isAdminRoute(path) && !"ROLE_ADMIN".equals(role)) {
                addCors(exchange);
                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                return exchange.getResponse().setComplete();
            }

            if (isCustomerRoute(path) && !"ROLE_CUSTOMER".equals(role)) {
                addCors(exchange);
                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                return exchange.getResponse().setComplete();
            }

            // ---------- Forward user identity ----------
            ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                    .header("X-USER-ID", userId)
                    .header("X-USER-ROLE", role)
                    .build();

            return chain.filter(exchange.mutate().request(mutatedRequest).build());

        } catch (Exception e) {
            addCors(exchange);
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }

    // ---------- CORS ----------
    private void addCors(ServerWebExchange exchange) {
        exchange.getResponse().getHeaders().set("Access-Control-Allow-Origin", "http://localhost:3000");
        exchange.getResponse().getHeaders().set("Access-Control-Allow-Credentials", "true");
        exchange.getResponse().getHeaders().set("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        exchange.getResponse().getHeaders().set("Access-Control-Allow-Headers", "Authorization, Content-Type");
    }

    private boolean isAdminRoute(String path) {
        return path.startsWith("/api/v1/admin/orders")
                || path.startsWith("/api/v1/admin/products");
    }

    private boolean isCustomerRoute(String path) {
        return path.startsWith("/api/v1/cart")
                || (path.startsWith("/api/v1/orders") && !path.startsWith("/api/v1/admin/orders"))
                || path.startsWith("/api/v1/users");
    }

}
