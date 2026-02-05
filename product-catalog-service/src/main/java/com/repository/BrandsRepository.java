package com.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.Brands;

public interface BrandsRepository extends JpaRepository<Brands, Long> {

    Optional<Brands> findByBrandNameIgnoreCase(String brandName);

    List<Brands> findByBrandNameContainingIgnoreCase(String brandNamePart);

    boolean existsByBrandNameIgnoreCase(String brandName);
}
