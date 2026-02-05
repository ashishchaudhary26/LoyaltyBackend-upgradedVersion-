package com.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.Product_Stock;

public interface ProductStockRepository extends JpaRepository<Product_Stock, Long> {

    Optional<Product_Stock> findByProductId(Long productId);

    boolean existsByProductIdAndAvailableQuantityGreaterThan(Long productId, Integer minQty);
}
