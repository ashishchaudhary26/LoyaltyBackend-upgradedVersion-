package com.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "brands")
@Schema(name = "Brand", description = "Represents a brand/manufacturer of products")
public class Brands {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the brand", example = "1",
            accessMode = Schema.AccessMode.READ_ONLY)
    public Long id;

    @Column(name = "brand_name", nullable = false, length = 255)
    @Schema(description = "Name of the brand", example = "Apple")
    public String brandName;

    @Column(columnDefinition = "TEXT")
    @Schema(description = "Detailed description of the brand",
            example = "Apple Inc. is an American multinational technology company.")
    public String description;

    @Column(name = "created_at")
    @Schema(description = "Timestamp when the brand record was created",
            example = "2025-11-25T10:15:30")
    public LocalDateTime createdAt;

    @OneToMany(mappedBy = "brand", fetch = FetchType.LAZY)
    @Schema(description = "List of products belonging to this brand")
    public List<Products> products;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public List<Products> getProducts() {
		return products;
	}

	public void setProducts(List<Products> products) {
		this.products = products;
	}
}
