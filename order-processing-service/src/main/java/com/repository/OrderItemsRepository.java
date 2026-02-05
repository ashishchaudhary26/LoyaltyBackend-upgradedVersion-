package com.repository;

import com.entity.OrderItems;
import com.entity.Orders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems, Long> {

    List<OrderItems> findByOrderId(Long orderId);

    void deleteByOrderId(Long orderId);

    List<OrderItems> findByProductId(Long productId);

}
