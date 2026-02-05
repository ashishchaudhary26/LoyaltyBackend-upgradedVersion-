package com.dto;

import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request object for updating user profile details")
public class UpdateProfileRequest {

    @Schema(
        description = "Updated full name of the user",
        example = "Jane Doe",
        maxLength = 255
    )
    @Size(max = 255)
    private String fullName;

    @Schema(
        description = "Updated mobile number of the user",
        example = "+1234567890",
        maxLength = 20
    )
    @Size(max = 20)
    private String mobileNumber;

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getMobileNumber() { return mobileNumber; }
    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }
}
