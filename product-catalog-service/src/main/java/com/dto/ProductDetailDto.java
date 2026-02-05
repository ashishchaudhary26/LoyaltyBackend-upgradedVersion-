package com.dto;

import java.math.BigDecimal;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "ProductDetailDto",
    description = "Detailed information about a product including images and stock info"
)
public class ProductDetailDto {

    @Schema(
        description = "Primary key of the product",
        example = "101",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long id;

    @Schema(
        description = "SKU identifier for the product",
        example = "SKU-12345"
    )
    private String sku;

    @Schema(
        description = "Name of the product",
        example = "Apple iPhone 14 Pro Max"
    )
    private String productName;

    @Schema(
        description = "Short summary or highlight description",
        example = "6.7-inch display, 128GB storage"
    )
    private String shortDescription;

    @Schema(
        description = "Full detailed description of the product"
    )
    private String description;

    @Schema(
        description = "Current selling price of the product",
        example = "999.99"
    )
    private BigDecimal price;

    @Schema(
        description = "Availability status of the product",
        example = "true"
    )
    private boolean isAvailable;

    @Schema(
        description = "Brand ID to which the product belongs",
        example = "5"
    )
    private Long brandId;

    @Schema(
        description = "Category ID of the product",
        example = "12"
    )
    private Long categoryId;

    @Schema(
        description = "List of image metadata for the product",
        implementation = ProductImageDto.class
    )
    private List<ProductImageDto> images;

    @Schema(
        description = "Stock information for the product",
        implementation = ProductStockDto.class
    )
    private ProductStockDto stock;

    public ProductDetailDto() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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

    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }

    public Long getBrandId() { return brandId; }
    public void setBrandId(Long brandId) { this.brandId = brandId; }

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    public List<ProductImageDto> getImages() { return images; }
    public void setImages(List<ProductImageDto> images) { this.images = images; }

    public ProductStockDto getStock() { return stock; }
    public void setStock(ProductStockDto stock) { this.stock = stock; }
}
