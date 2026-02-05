package com.service;

import com.dto.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    Page<ProductDto> listAvailable(Pageable pageable);

    Page<ProductDto> search(Long categoryId, Long brandId,
                            java.math.BigDecimal minPrice, java.math.BigDecimal maxPrice,
                            String keyword,
                            Pageable pageable);

    ProductDetailDto getProductDetails(Long productId);

    ProductDto getProductBasic(Long productId);

    ProductDto createProduct(CreateProductRequest req);

    ProductDto updateProduct(Long productId, UpdateProductRequest req);

    void softDeleteProduct(Long productId);

    ProductStockDto updateStock(Long productId, UpdateStockRequest req);
    List<BrandDto> listBrands();
    List<CategoryDto> listCategories();
    CategoryDto createCategory(CategoryDto dto);
    BrandDto createBrand(BrandDto dto);

    List<ProductImageDto> getProductImages(Long productId);

    List<ProductReviewDto> getProductReviews(Long productId);
    ProductReviewDto addProductReview(Long productId, Long userId, CreateReviewRequest req);

    ProductDto getProductBySku(String sku);

    // image helper methods
    ProductImageDto addProductImage(Long productId, ProductImageDto dto);
    void deleteProductImage(Long productId, Long imageId);
    ProductStockDto getStock(Long productId);

    void reserveStock(Long productId, Integer qty);

    void commitStock(Long productId, Integer qty);

    void releaseStock(Long productId, Integer qty);
}
