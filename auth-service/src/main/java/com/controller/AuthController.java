package com.controller;

import com.dto.AuthResponse;
import com.dto.ChangePasswordRequest;
import com.dto.LoginRequest;
import com.dto.LogoutRequest;
import com.dto.ProfileResponse;
import com.dto.RefreshTokenRequest;
import com.dto.RegisterRequest;
import com.dto.UpdateProfileRequest;
import com.entity.Users;
import com.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Tag(name = "Authentication", description = "Endpoints for user registration, login, profile management, and password change")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Register a new user", description = "Creates a new user and returns authentication details")
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest req) {
        AuthResponse resp = authService.register(req);
        return ResponseEntity.status(201).body(resp);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request,
            HttpServletResponse response) {
        AuthResponse authResponse = authService.login(request);

        // Set refresh token in HttpOnly cookie
        Cookie cookie = new Cookie("refreshToken", authResponse.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // true in production (HTTPS)
        cookie.setPath("/auth");
        cookie.setMaxAge(7 * 24 * 60 * 60); // 7 days

        response.addCookie(cookie);

        // Do NOT send refresh token in body to frontend
        authResponse.setRefreshToken(null);

        return ResponseEntity.ok(authResponse);
    }

    @Operation(summary = "Get current user profile", description = "Fetches profile information of the currently authenticated user")
    @GetMapping("/profile")
    public ResponseEntity<ProfileResponse> profile(@RequestHeader("X-USER-ID") Long userId) {
        return authService.findById(userId)
                .map(this::toProfileResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update current user profile", description = "Update full name and mobile number of the authenticated user")
    @PutMapping("/profile")
    public ResponseEntity<ProfileResponse> updateProfile(
            @RequestHeader("X-USER-ID") Long userId,
            @Valid @RequestBody UpdateProfileRequest req) {

        Users updated = authService.updateProfile(userId, req);
        return ResponseEntity.ok(toProfileResponse(updated));
    }

    @Operation(summary = "Change current user password", description = "Allows the authenticated user to change their password")
    @PutMapping("/profile/password")
    public ResponseEntity<Void> changePassword(
            @RequestHeader("X-USER-ID") Long userId,
            @Valid @RequestBody ChangePasswordRequest req) {

        authService.changePassword(userId, req);
        return ResponseEntity.noContent().build();
    }

    private ProfileResponse toProfileResponse(Users u) {
        return new ProfileResponse(
                u.getId(),
                u.getEmail(),
                u.getFullName(),
                u.getMobileNumber(),
                u.getRoleName(),
                u.getIsActive(),
                u.getCreatedAt(),
                u.getUpdatedAt());
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("X-USER-ID") Long userId) {

        System.out.println("LOGOUT userId = " + userId);

        authService.logoutByUserId(userId);
        return ResponseEntity.noContent().build();
    }

    // reward
    @PutMapping("/{userId}/add-reward")
    public void addReward(@PathVariable Long userId,
            @RequestParam Double amount) {
        authService.addReward(userId, amount);
    }

}
