package com.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request object for user login/authentication")
public class LoginRequest {

    @Schema(
        description = "User email used for logging in",
        example = "user@example.com",
        required = true
    )
    @Email
    @NotBlank
    private String email;

    @Schema(
        description = "User password used for logging in",
        example = "Password123!",
        required = true
    )
    @NotBlank
    private String password;

    // Getters & Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
