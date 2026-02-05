package com.repository;

import com.entity.Product_Images;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<Product_Images, Long> {

    List<Product_Images> findByProductId(Long productId);

    // For list page (main image)
    Product_Images findFirstByProductIdOrderBySortOrderAscIdAsc(Long productId);

    // For product detail page (all images)
    List<Product_Images> findByProductIdOrderBySortOrderAscIdAsc(Long productId);
}

