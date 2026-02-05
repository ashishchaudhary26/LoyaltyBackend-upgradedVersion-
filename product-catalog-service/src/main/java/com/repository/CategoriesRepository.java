package com.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.Categories;

public interface CategoriesRepository extends JpaRepository<Categories, Long> {

    Optional<Categories> findByCategoryNameIgnoreCase(String categoryName);

    List<Categories> findByCategoryNameContainingIgnoreCase(String namePart);

    boolean existsByCategoryNameIgnoreCase(String categoryName);
}
