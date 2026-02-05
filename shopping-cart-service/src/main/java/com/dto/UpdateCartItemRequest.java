package com.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "UpdateCartItemRequest",
    description = "Request payload to update the quantity of an existing cart item"
)
public class UpdateCartItemRequest {

    @NotNull
    @Min(1)
    @Schema(
        description = "Updated quantity for the cart item (must be at least 1)",
        example = "3",
        required = true
    )
    private Integer quantity;

    @Schema(
        description = "User ID of the authenticated user (optional for guest carts)",
        example = "42",
        required = false
    )
    private Long userId;

    public UpdateCartItemRequest() {}

    public UpdateCartItemRequest(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}
