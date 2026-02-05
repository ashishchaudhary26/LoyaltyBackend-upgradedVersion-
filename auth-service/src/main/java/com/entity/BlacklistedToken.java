package com.entity;
import javax.persistence.*;


import java.time.Instant;

@Entity
public class BlacklistedToken {

    @Id
    private String token;

    private Instant expiry;

    public BlacklistedToken() {}

    public BlacklistedToken(String token, Instant expiry) {
        this.token = token;
        this.expiry = expiry;
    }

    public String getToken() {
        return token;
    }

    public Instant getExpiry() {
        return expiry;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setExpiry(Instant expiry) {
        this.expiry = expiry;
    }
}
