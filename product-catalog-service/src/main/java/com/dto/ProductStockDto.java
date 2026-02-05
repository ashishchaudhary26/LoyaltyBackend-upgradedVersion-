package com.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "ProductStockDto",
    description = "Represents the stock details of a product, including available and reserved quantities"
)
public class ProductStockDto {

    @Schema(
        description = "Identifier of the product whose stock is represented",
        example = "101",
        required = true
    )
    private Long productId;

    @Schema(
        description = "Quantity of the product available for purchase",
        example = "50",
        required = true
    )
    private Integer availableQuantity;

    @Schema(
        description = "Quantity reserved for orders but not yet deducted from stock",
        example = "5"
    )
    private Integer reservedQuantity;

    public ProductStockDto() {}

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public Integer getAvailableQuantity() { return availableQuantity; }
    public void setAvailableQuantity(Integer availableQuantity) { this.availableQuantity = availableQuantity; }

    public Integer getReservedQuantity() { return reservedQuantity; }
    public void setReservedQuantity(Integer reservedQuantity) { this.reservedQuantity = reservedQuantity; }
}
