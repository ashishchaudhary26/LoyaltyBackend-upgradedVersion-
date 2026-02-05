package com.repository;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.entity.Products;

public interface ProductsRepository extends JpaRepository<Products, Long> {

    Optional<Products> findBySku(String sku);

    Page<Products> findByIsAvailableTrue(Pageable pageable);

    Page<Products> findByCategoryIdAndIsAvailableTrue(Long categoryId, Pageable pageable);

    Page<Products> findByBrandIdAndIsAvailableTrue(Long brandId, Pageable pageable);

    Page<Products> findByProductNameContainingIgnoreCaseAndIsAvailableTrue(
            String productName,
            Pageable pageable
    );

    @Query(
        "SELECT p FROM Products p " +
        "WHERE p.isAvailable = true " +
        "  AND (:categoryId IS NULL OR p.categoryId = :categoryId) " +
        "  AND (:brandId IS NULL OR p.brandId = :brandId) " +
        "  AND (:minPrice IS NULL OR p.price >= :minPrice) " +
        "  AND (:maxPrice IS NULL OR p.price <= :maxPrice) " +
        "  AND ( :keyword IS NULL " +
        "        OR LOWER(p.productName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
        "        OR LOWER(p.shortDescription) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
        "      )"
    )
    Page<Products> searchProducts(
            @Param("categoryId") Long categoryId,
            @Param("brandId") Long brandId,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("keyword") String keyword,
            Pageable pageable
    );
}
