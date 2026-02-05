package com.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "ProductImageDto",
    description = "Represents metadata about a product image"
)
public class ProductImageDto {

    @Schema(
        description = "Primary key of the image record",
        example = "1",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long id;

    @Schema(
        description = "Internal storage key for the image (usually in cloud storage like AWS S3)",
        example = "products/iphone14/image1.jpg"
    )
    private String imageKey;

    @Schema(
        description = "Publicly accessible URL of the product image",
        example = "https://cdn.mystore.com/products/iphone14/image1.jpg"
    )
    private String imageUrl;

    @Schema(
        description = "Alternative text for accessibility and SEO",
        example = "Front view of iPhone 14 Pro Max"
    )
    private String altText;

    @Schema(
        description = "Order in which the image should appear in UI carousels",
        example = "1"
    )
    private Integer sortOrder;

    public ProductImageDto() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getImageKey() { return imageKey; }
    public void setImageKey(String imageKey) { this.imageKey = imageKey; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getAltText() { return altText; }
    public void setAltText(String altText) { this.altText = altText; }

    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }
}
