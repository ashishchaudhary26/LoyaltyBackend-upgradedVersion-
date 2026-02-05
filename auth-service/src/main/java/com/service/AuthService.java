package com.service;

import com.dto.AuthResponse;
import com.dto.ChangePasswordRequest;
import com.dto.LoginRequest;
import com.dto.RegisterRequest;
import com.dto.UpdateProfileRequest;
import com.entity.Users;

import java.util.Optional;

public interface AuthService {
    AuthResponse register(RegisterRequest req);

    AuthResponse login(LoginRequest req);

    Optional<Users> findById(Long id);

    Users updateProfile(Long userId, UpdateProfileRequest req);

    Optional<Users> findByEmail(String email);

    void changePassword(Long userId, ChangePasswordRequest req);

    //
    void logoutByUserId(Long userId);
    //

    // void deleteByUser(Users user);

    // AuthResponse refreshToken(String refreshToken);

    AuthResponse refreshAccessToken(String refreshToken);

}
