package com.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CartItemDto", description = "Represents an individual item inside a user's cart")
public class CartItemDto {

    @Schema(description = "Primary key of the cart item", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Identifier of the product added to the cart", example = "101", required = true)
    private Long productId;

    @Schema(description = "Quantity of the product in the cart", example = "2", required = true)
    private Integer quantity;

    @Schema(description = "Unit price of the product at the time it was added", example = "499.99")
    private BigDecimal unitPrice;

    @Schema(description = "Timestamp when the item was added to the cart", example = "2025-11-25T12:34:56", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime addedAt;

    @Schema(description = "Timestamp when the item was last updated", example = "2025-11-25T12:45:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;

    public CartItemDto() {}

    public CartItemDto(Long id, Long productId, Integer quantity, BigDecimal unitPrice,
                       LocalDateTime addedAt, LocalDateTime updatedAt) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.addedAt = addedAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }

    public LocalDateTime getAddedAt() { return addedAt; }
    public void setAddedAt(LocalDateTime addedAt) { this.addedAt = addedAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
