package com.repository;

import com.entity.Orders;
import com.entity.Payments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface PaymentsRepository extends JpaRepository<Payments, Long> {

    Optional<Payments> findByOrderId(Long orderId);

    List<Payments> findByPaymentStatus(String paymentStatus);

}
