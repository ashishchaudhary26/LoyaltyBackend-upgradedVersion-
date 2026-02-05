package com.repository;

import com.entity.Product_Reviews;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductReviewRepository extends JpaRepository<Product_Reviews, Long> {

    List<Product_Reviews> findByProductId(Long productId);
    

}
