package com.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "UpdateStockRequest",
    description = "Request payload for updating the available stock quantity of a product (admin operation)"
)
public class UpdateStockRequest {

    @NotNull
    @Min(0)
    @Schema(
        description = "Updated available quantity for the product (must be 0 or above)",
        example = "25",
        required = true
    )
    private Integer availableQuantity;

    public UpdateStockRequest() {}

    public Integer getAvailableQuantity() { return availableQuantity; }
    public void setAvailableQuantity(Integer availableQuantity) { this.availableQuantity = availableQuantity; }
}
