package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.BlacklistedToken;

public interface BlacklistedTokenRepository extends JpaRepository<BlacklistedToken, String> {
        boolean existsByToken(String token);

}
