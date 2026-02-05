package com.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "UpdateProductRequest",
    description = "Payload for updating product details (admin-only operation)"
)
public class UpdateProductRequest {

    @NotBlank
    @Schema(
        description = "Updated name of the product",
        example = "Wireless Headphones Pro",
        required = true
    )
    private String productName;

    @Schema(
        description = "Short summary of the product",
        example = "Premium wireless headphones with noise cancellation"
    )
    private String shortDescription;

    @Schema(
        description = "Detailed product description",
        example = "These wireless headphones offer high-fidelity audio, long battery life, and comfortable ear cushions."
    )
    private String description;

    @NotNull
    @Schema(
        description = "Updated price of the product",
        example = "149.99",
        required = true
    )
    private BigDecimal price;

    @Schema(
        description = "Availability status of the product",
        example = "true"
    )
    private Boolean isAvailable;

    @Schema(
        description = "Updated brand ID of the product",
        example = "3"
    )
    private Long brandId;

    @Schema(
        description = "Updated category ID of the product",
        example = "7"
    )
    private Long categoryId;

    public UpdateProductRequest() {}

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
