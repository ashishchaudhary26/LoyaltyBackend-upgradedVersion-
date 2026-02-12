package com.service.impl;

import com.dto.*;
import com.entity.BlacklistedToken;
import com.entity.RefreshToken;
import com.entity.Role;
import com.entity.Users;
import com.repository.BlacklistedTokenRepository;
import com.repository.RefreshTokenRepository;
import com.repository.RoleRepository;
import com.repository.UserRepository;
import com.service.AuthService;
import com.service.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private BlacklistedTokenRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Override
    @Transactional
    public void logoutByUserId(Long userId) {

        System.out.println("Deleting refresh token for userId = " + userId);

        refreshTokenRepository.deleteByUserId(userId);
    }

    // @Transactional
    // public void logoutByUserId(Long userId) {

    // System.out.println("Deleting refresh token for userId = " + userId);

    // Users user = userRepository.findById(userId)
    // .orElseThrow(() -> new RuntimeException("User not found"));

    // refreshTokenRepository.deleteByUser(user);
    // }

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest req) {

        if (userRepository.existsByEmail(req.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
        }

        Users user = new Users();
        user.setEmail(req.getEmail());
        user.setPasswordHash(passwordEncoder.encode(req.getPassword()));
        user.setFullName(req.getFullName());
        user.setMobileNumber(req.getMobileNumber());

        user.setRoleName("ROLE_CUSTOMER");

        Role role = roleRepository.findByName("ROLE_CUSTOMER")
                .orElse(null);
        user.setRole(role);

        userRepository.save(user);

        return new AuthResponse(
                null,
                null,
                user.getId(),
                user.getEmail(),
                user.getRoleName());
    }

    @Override
    @Transactional
    public AuthResponse login(LoginRequest req) {

        Users user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));

        if (!passwordEncoder.matches(req.getPassword(), user.getPasswordHash())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        refreshTokenRepository.deleteByUserId(user.getId());

        // create new refresh token
        String refreshToken = UUID.randomUUID().toString();
        refreshTokenRepository.save(new RefreshToken(user.getId(), refreshToken));

        System.out.println("refreshToken :" + refreshToken);

        String accessToken = jwtService.generateToken(
                user.getId(),
                user.getEmail(),
                user.getRoleName());

        return new AuthResponse(
                accessToken,
                refreshToken,
                user.getId(),
                user.getEmail(),
                user.getRoleName());

    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Users> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Users> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public Users updateProfile(Long userId, UpdateProfileRequest req) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        boolean changed = false;
        if (req.getFullName() != null) {
            user.setFullName(req.getFullName().trim());
            changed = true;
        }
        if (req.getMobileNumber() != null) {
            user.setMobileNumber(req.getMobileNumber().trim());
            changed = true;
        }

        if (changed) {
            user = userRepository.save(user);
        }
        return user;
    }

    @Override
    @Transactional
    public void changePassword(Long userId, ChangePasswordRequest req) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (!passwordEncoder.matches(req.getOldPassword(), user.getPasswordHash())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Old password is incorrect");
        }

        user.setPasswordHash(passwordEncoder.encode(req.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public AuthResponse refreshAccessToken(String refreshToken) {

        if (refreshToken == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh token missing");
        }

        RefreshToken token = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid refresh token"));

        Users user = userRepository.findById(token.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        String newAccessToken = jwtService.generateToken(
                user.getId(),
                user.getEmail(),
                user.getRoleName());

        return new AuthResponse(newAccessToken, null,
                user.getId(), user.getEmail(), user.getRoleName());
    }

    @Override
    public void addReward(Long userId, Double amount) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setRewardBalance(user.getRewardBalance() + amount);
        userRepository.save(user);
    }

}
