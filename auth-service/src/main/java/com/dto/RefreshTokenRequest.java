package com.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class RefreshTokenRequest {
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
