package com.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "CreateProductRequest",
    description = "Payload for creating a new product (admin only)"
)
public class CreateProductRequest {

    @NotBlank
    @Schema(
        description = "Unique SKU code for the product",
        example = "SKU-IPHONE15-256GB",
        required = true
    )
    private String sku;

    @NotBlank
    @Schema(
        description = "Name of the product",
        example = "iPhone 15 Pro Max",
        required = true
    )
    private String productName;

    @Schema(
        description = "Short marketing description for listing views",
        example = "Latest model with A17 chip"
    )
    private String shortDescription;

    @Schema(
        description = "Detailed description of the product",
        example = "The iPhone 15 Pro Max features a titanium body, enhanced camera system, and A17 Bionic chip."
    )
    private String description;

    @NotNull
    @Schema(
        description = "Price of the product",
        example = "1299.99",
        required = true
    )
    private BigDecimal price;

    @Schema(
        description = "Product availability status",
        example = "true",
        defaultValue = "true"
    )
    private Boolean isAvailable = true;

    @Schema(
        description = "Brand ID to which the product belongs",
        example = "3"
    )
    private Long brandId;

    @Schema(
        description = "Category ID of the product",
        example = "12"
    )
    private Long categoryId;

    public CreateProductRequest() {}

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getShortDescription() { return shortDescription; }
    public void setShortDescription(String shortDescription) { this.shortDescription = shortDescription; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Boolean getIsAvailable() { return isAvailable; }
    public void setIsAvailable(Boolean isAvailable) { this.isAvailable = isAvailable; }

    public Long getBrandId() { return brandId; }
    public void setBrandId(Long brandId) { this.brandId = brandId; }

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
}
