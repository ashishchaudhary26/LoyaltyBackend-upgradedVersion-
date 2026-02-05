package com.entity;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false, unique = true)
    private Long userId;

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    // @ManyToOne(fetch = FetchType.EAGER)
    // @JoinColumn(name = "user_id", nullable = false)
    // private Users user;

    public RefreshToken() {
    }

    public RefreshToken(Long userId, String token) {
        this.userId = userId;
        this.token = token;
        this.createdAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public Long getUserId() {
        return userId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setToken(String token) {
        this.token = token;
    }

    // public Users getUser() {
    // return user;
    // }

    // public void setUser(Users user) {
    // this.user = user;
    // }
}
// @Entity
// @Table(name = "refresh_tokens")
// public class RefreshToken {

// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Long id;

// @Column(nullable = false, unique = true)
// private String token;

// @ManyToOne(fetch = FetchType.EAGER)
// @JoinColumn(name = "user_id", nullable = false)
// private Users user;

// private LocalDateTime createdAt;

// // getters & setters
// public Users getUser() {
// return user;
// }

// public void setUser(Users user) {
// this.user = user;
// }

// public Long getId() {
// return id;
// }

// public void setId(Long id) {
// this.id = id;
// }

// public String getToken() {
// return token;
// }

// public void setToken(String token) {
// this.token = token;
// }

// public LocalDateTime getCreatedAt() {
// return createdAt;
// }

// public void setCreatedAt(LocalDateTime createdAt) {
// this.createdAt = createdAt;
// }

// }
