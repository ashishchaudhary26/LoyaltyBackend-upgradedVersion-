package com.dto;

import java.math.BigDecimal;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "AddCartItemRequest", description = "Request payload to add an item to a cart")
public class AddCartItemRequest {

    @Schema(description = "UUID of the cart (used for guest carts). If provided, userId may be null for guest flows.", example = "550e8400-e29b-41d4-a716-446655440000", required = false)
    private String cartUuid;

    @Schema(description = "Identifier of the logged-in user (used for user carts). If provided, cartUuid may be null.", example = "42", required = false)
    private Long userId;

    @NotNull
    @Schema(description = "Identifier of the product to add", example = "101", required = true)
    private Long productId;

    @NotNull
    @Min(1)
    @Schema(description = "Quantity of the product to add (minimum 1)", example = "2", required = true)
    private Integer quantity;

    @Schema(description = "Unit price at the time of adding (optional; backend can compute/verify)", example = "499.99", required = false)
    private BigDecimal unitPrice;

    public AddCartItemRequest() {}

    public String getCartUuid() { return cartUuid; }
    public void setCartUuid(String cartUuid) { this.cartUuid = cartUuid; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
}
