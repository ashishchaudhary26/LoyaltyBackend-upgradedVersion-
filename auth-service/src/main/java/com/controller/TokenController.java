package com.controller;

import com.dto.AuthResponse;
import com.repository.BlacklistedTokenRepository;
import com.service.AuthService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class TokenController {

    private final BlacklistedTokenRepository repository;
    private final AuthService authService;

    public TokenController(BlacklistedTokenRepository repository, AuthService authService) {
        this.repository = repository;
        this.authService = authService;
    }

    @GetMapping("/token/blacklisted")
    public boolean isBlacklisted(@RequestParam String token) {
        return repository.existsByToken(token);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(
            @CookieValue(name = "refreshToken", required = false) String refreshToken) {

        AuthResponse response = authService.refreshAccessToken(refreshToken);
        return ResponseEntity.ok(response);
    }
}
