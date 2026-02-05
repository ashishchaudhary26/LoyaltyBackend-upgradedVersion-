package com.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response object containing user profile information")
public class ProfileResponse {

    @Schema(
        description = "Unique identifier of the user",
        example = "101"
    )
    private Long id;

    @Schema(
        description = "User's registered email address",
        example = "john.doe@example.com"
    )
    private String email;

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

    @Schema(
        description = "Role assigned to the user",
        example = "ROLE_USER"
    )
    private String role;

    @Schema(
        description = "Indicates whether the user account is active",
        example = "true"
    )
    private Boolean isActive;

    @Schema(
        description = "Date and time when the user account was created",
        example = "2024-01-15T10:30:45"
    )
    private LocalDateTime createdAt;

    @Schema(
        description = "Date and time when the user account was last updated",
        example = "2024-02-10T14:20:15"
    )
    private LocalDateTime updatedAt;

    public ProfileResponse() {}

    public ProfileResponse(Long id, String email, String fullName, String mobileNumber,
                           String role, Boolean isActive,
                           LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.mobileNumber = mobileNumber;
        this.role = role;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getMobileNumber() { return mobileNumber; }
    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
