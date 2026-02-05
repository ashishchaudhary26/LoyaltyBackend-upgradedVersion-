package com.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request object for updating/changing the user's password")
public class ChangePasswordRequest {

    @Schema(
        description = "User's current password",
        example = "OldPassword123!",
        required = true
    )
    @NotBlank
    private String oldPassword;

    @Schema(
        description = "New password the user wants to set",
        example = "NewPassword123!",
        minLength = 8,
        required = true
    )
    @NotBlank
    @Size(min = 8, message = "New password must be at least 8 characters")
    private String newPassword;

    // Getters & Setters
    public String getOldPassword() { return oldPassword; }
    public void setOldPassword(String oldPassword) { this.oldPassword = oldPassword; }

    public String getNewPassword() { return newPassword; }
    public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
}
