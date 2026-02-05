package com.controller;

import com.dto.*;
import com.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@Tag(name = "Products", description = "Product Catalog APIs (customer + admin)")
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // ---------------- Public Endpoints ----------------

    @Operation(summary = "List all available products (paged)")
    @GetMapping
    public ResponseEntity<Page<ProductDto>> list(
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(productService.listAvailable(pageable));
    }

    @Operation(summary = "Search products with filters")
    @GetMapping("/search")
    public ResponseEntity<Page<ProductDto>> search(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long brandId,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String keyword,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(productService.search(categoryId, brandId, minPrice, maxPrice, keyword, pageable));
    }

    @Operation(summary = "Get detailed product information")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailDto> details(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductDetails(id));
    }

    @Operation(summary = "Get product images")
    @GetMapping("/{id}/images")
    public ResponseEntity<List<ProductImageDto>> images(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductImages(id));
    }

    @Operation(summary = "List all brands")
    @GetMapping("/brands")
    public ResponseEntity<List<BrandDto>> brands() {
        return ResponseEntity.ok(productService.listBrands());
    }

    @Operation(summary = "List all categories")
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> categories() {
        return ResponseEntity.ok(productService.listCategories());
    }

    @Operation(summary = "Get product reviews")
    @GetMapping("/{id}/reviews")
    public ResponseEntity<List<ProductReviewDto>> reviews(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductReviews(id));
    }

    // ---------------- Authenticated Endpoints ----------------

    @Operation(summary = "Add product review (logged-in customers)")
    @PostMapping("/{id}/reviews")
    public ResponseEntity<ProductReviewDto> addReview(
            @PathVariable Long id,
            @RequestHeader("X-USER-ID") Long userId,
            @Valid @RequestBody CreateReviewRequest req) {
        System.out.println(" BACKEND RECEIVED REVIEW FOR PRODUCT: " + id);
        System.out.println(" From User ID: " + userId);
        System.out.println(" Rating: " + req.getRating());
        System.out.println(" Title: " + req.getReviewTitle());
        System.out.println(" Text: " + req.getReviewText());

        req.setUserId(userId);

        ProductReviewDto dto = productService.addProductReview(id, userId, req);
        System.out.println("✅ Review stored successfully!");

        return ResponseEntity.status(201).body(dto);
    }

    @Operation(summary = "Get stock for a product")
    @GetMapping("/{id}/stock")
    public ResponseEntity<ProductStockDto> getStock(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getStock(id));
    }

    @Operation(summary = "Reserve stock for a product (internal)")
    @PostMapping("/{id}/stock/reserve")
    public ResponseEntity<Void> reserve(
            @PathVariable Long id,
            @RequestParam("qty") Integer qty) {
        productService.reserveStock(id, qty);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Commit stock (after successful payment)")
    @PostMapping("/{id}/stock/commit")
    public ResponseEntity<Void> commit(
            @PathVariable Long id,
            @RequestParam("qty") Integer qty) {
        productService.commitStock(id, qty);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Release stock (on payment failure/cancel)")
    @PostMapping("/{id}/stock/release")
    public ResponseEntity<Void> release(
            @PathVariable Long id,
            @RequestParam("qty") Integer qty) {
        productService.releaseStock(id, qty);
        return ResponseEntity.noContent().build();
    }

}
