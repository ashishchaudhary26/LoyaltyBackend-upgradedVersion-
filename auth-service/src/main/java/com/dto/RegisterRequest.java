package com.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request object used for registering a new user")
public class RegisterRequest {

    @Schema(
        description = "Email address of the new user",
        example = "john.doe@example.com",
        required = true
    )
    @Email
    @NotBlank
    private String email;

    @Schema(
        description = "Password for the new user account",
        example = "StrongPassword123!",
        required = true
    )
    @NotBlank
    private String password;

    @Schema(
        description = "Full name of the user",
        example = "John Doe"
    )
    private String fullName;

    @Schema(
        description = "Mobile phone number of the user",
        example = "+1234567890"
    )
    private String mobileNumber;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getMobileNumber() { return mobileNumber; }
    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }
}
