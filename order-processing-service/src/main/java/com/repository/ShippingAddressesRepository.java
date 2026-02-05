package com.repository;

import com.entity.Orders;
import com.entity.ShippingAddresses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShippingAddressesRepository extends JpaRepository<ShippingAddresses, Long> {
    List<ShippingAddresses> findByUserId(Long userId);

    void deleteByIdAndUserId(Long id, Long userId);

}
