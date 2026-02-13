package com.controller;

import com.dto.*;
import com.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Admin Products", description = "Admin product management APIs")
@RestController
@RequestMapping("/api/v1/admin/products") // admin/products
public class AdminProductController {

    private final ProductService productService;

    public AdminProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductDto> create(
            @RequestHeader("X-USER-ID") Long userId,
            @Valid @RequestBody CreateProductRequest req) {
        return ResponseEntity.status(201).body(productService.createProduct(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> update(
            @PathVariable Long id,
            @RequestHeader("X-USER-ID") Long userId,
            @Valid @RequestBody UpdateProductRequest req) {
        return ResponseEntity.ok(productService.updateProduct(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @RequestHeader("X-USER-ID") Long userId) {
        productService.softDeleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<ProductStockDto> updateStock(
            @PathVariable Long id,
            @RequestHeader("X-USER-ID") Long userId,
            @Valid @RequestBody UpdateStockRequest req) {
        return ResponseEntity.ok(productService.updateStock(id, req));
    }

    @PostMapping("/{id}/images")
    public ResponseEntity<ProductImageDto> addImage(
            @PathVariable Long id,
            @RequestHeader("X-USER-ID") Long userId,
            @Valid @RequestBody ProductImageDto imageDto) {
        return ResponseEntity.status(201).body(productService.addProductImage(id, imageDto));
    }

    @DeleteMapping("/{id}/images/{imageId}")
    public ResponseEntity<Void> deleteImage(
            @PathVariable Long id,
            @PathVariable Long imageId,
            @RequestHeader("X-USER-ID") Long userId) {
        productService.deleteProductImage(id, imageId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/categories")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto dto) {
        return ResponseEntity.status(201).body(productService.createCategory(dto));
    }

    @PostMapping("/brands")
    public ResponseEntity<BrandDto> createBrand(@Valid @RequestBody BrandDto dto) {
        return ResponseEntity.status(201).body(productService.createBrand(dto));
    }

    // reward new fature added on 11/02
    @PostMapping("/rewards/{productId}")
    public ResponseEntity<String> saveReward(
            @PathVariable Long productId,
            @RequestParam Double percentage,
            @RequestParam Boolean active) {

        productService.saveOrUpdateReward(productId, percentage, active);

        return ResponseEntity.ok("Reward saved successfully");
    }

}
