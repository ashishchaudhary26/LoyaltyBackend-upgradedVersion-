package com.repository;

import com.entity.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItems, Long> {

    List<CartItems> findByCartId(Long cartId);

    Optional<CartItems> findByCartIdAndProductId(Long cartId, Long productId);

    void deleteByCartId(Long cartId);
}
