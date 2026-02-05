package com.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "BrandDto",
    description = "Represents a product brand available in the catalog"
)
public class BrandDto {

    @Schema(
        description = "Primary key of the brand",
        example = "1",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long id;

    @Schema(
        description = "Name of the brand",
        example = "Apple",
        required = true
    )
    private String brandName;

    @Schema(
        description = "Short description or tagline for the brand",
        example = "Premium consumer electronics brand"
    )
    private String description;

    public BrandDto() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBrandName() { return brandName; }
    public void setBrandName(String brandName) { this.brandName = brandName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
