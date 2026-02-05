package com.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response object returned after successful authentication")
public class AuthResponse {

    @Schema(description = "JWT access token used for authorization", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String accessToken;

    @Schema(description = "Type of token provided", example = "Bearer", defaultValue = "Bearer")
    private String tokenType = "Bearer";

    @Schema(description = "Unique identifier of the authenticated user", example = "101")
    private Long userId;

    @Schema(description = "Email of the authenticated user", example = "user@example.com")
    private String email;

    @Schema(description = "Role assigned to the authenticated user", example = "ROLE_USER")
    private String role;

    @Schema(description = "Refresh token used to get new access token", example = "b7f2c0b1-8c4a-4d6a-9b6c-abc123xyz")
    private String refreshToken;

    public AuthResponse() {
    }

    public AuthResponse(
            String accessToken,
            String refreshToken,
            Long userId,
            String email,
            String role) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.userId = userId;
        this.email = email;
        this.role = role;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
