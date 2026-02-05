package com.repository;

import com.entity.Carts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartsRepository extends JpaRepository<Carts, Long> {

    Optional<Carts> findByCartUuid(String cartUuid);

    Optional<Carts> findByUserId(Long userId);

    boolean existsByUserId(Long userId);
}
