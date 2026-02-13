package com.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.entity.Reward;

public interface RewardRepository extends JpaRepository<Reward, Long> {

    Optional<Reward> findByProductIdAndActiveTrue(Long productId);
}
