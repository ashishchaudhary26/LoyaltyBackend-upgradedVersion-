package com.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "CategoryDto",
    description = "Represents a product category in the catalog"
)
public class CategoryDto {

    @Schema(
        description = "Primary key of the category",
        example = "10",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long id;

    @Schema(
        description = "Name of the product category",
        example = "Electronics",
        required = true
    )
    private String categoryName;

    public CategoryDto() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
}
