package com.dto;

import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CartDto", description = "Represents a shopping cart along with its items")
public class CartDto {

    @Schema(description = "Primary key of the cart", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "UUID of the cart (used to identify cart across sessions)", example = "550e8400-e29b-41d4-a716-446655440000")
    private String cartUuid;

    @Schema(description = "User ID who owns this cart (null for guest carts)", example = "42")
    private Long userId;

    @Schema(description = "List of items inside the cart", implementation = CartItemDto.class)
    private List<CartItemDto> items;

    @Schema(description = "Timestamp when the cart was created", example = "2025-11-25T12:34:56", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    @Schema(description = "Timestamp when the cart was last updated", example = "2025-11-25T12:45:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;

    public CartDto() {}

    public CartDto(Long id, String cartUuid, Long userId, List<CartItemDto> items,
                   LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.cartUuid = cartUuid;
        this.userId = userId;
        this.items = items;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCartUuid() { return cartUuid; }
    public void setCartUuid(String cartUuid) { this.cartUuid = cartUuid; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public List<CartItemDto> getItems() { return items; }
    public void setItems(List<CartItemDto> items) { this.items = items; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
