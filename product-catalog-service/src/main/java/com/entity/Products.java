package com.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "products")
@Schema(name = "Product", description = "Represents a product in the catalog")
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the product", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Column(nullable = false, length = 100)
    @Schema(description = "Stock Keeping Unit code of the product", example = "SKU-12345")
    private String sku;

    @Column(nullable = false, length = 512)
    @Schema(description = "Name of the product", example = "Apple iPhone 15 Pro")
    private String productName;

    @Column(length = 1000)
    @Schema(description = "Short summary of the product", example = "Latest iPhone with A17 Pro chip")
    private String shortDescription;

    @Column(columnDefinition = "TEXT")
    @Schema(description = "Detailed description of the product", example = "This iPhone features a 6.1-inch display, A17 Pro chip, and triple-camera system.")
    private String description;

    @Column(name = "brand_id")
    @Schema(description = "Foreign key to the brand", example = "10")
    private Long brandId;

    @Column(name = "category_id")
    @Schema(description = "Foreign key to the category", example = "3")
    private Long categoryId;

    @Column(nullable = false, precision = 12, scale = 2)
    @Schema(description = "Price of the product", example = "99999.99")
    private BigDecimal price;

    @Column(nullable = false)
    @Schema(description = "Whether the product is available for purchase", example = "true")
    private boolean isAvailable;

    @Column(name = "created_at", updatable = false)
    @Schema(description = "Timestamp when the product record was created", example = "2025-11-25T10:15:30")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @Schema(description = "Timestamp when the product record was last updated", example = "2025-11-25T11:00:00")
    private LocalDateTime updatedAt;
    @ManyToOne
    @JoinColumn(name = "brand_id", insertable = false, updatable = false)
    private Brands brand;
    @ManyToOne
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private Categories category;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Reward reward;

    public Reward getReward() {
        return reward;
    }

    public void setReward(Reward reward) {
        this.reward = reward;
    }

    public Brands getBrand() {
        return brand;
    }

    public void setBrand(Brands brand) {
        this.brand = brand;
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public int hashCode() {
        return Objects.hash(brandId, categoryId, createdAt, description, id, isAvailable, price, productName,
                shortDescription, sku, updatedAt);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Products other = (Products) obj;
        return Objects.equals(brandId, other.brandId) && Objects.equals(categoryId, other.categoryId)
                && Objects.equals(createdAt, other.createdAt) && Objects.equals(description, other.description)
                && Objects.equals(id, other.id) && isAvailable == other.isAvailable
                && Objects.equals(price, other.price) && Objects.equals(productName, other.productName)
                && Objects.equals(shortDescription, other.shortDescription) && Objects.equals(sku, other.sku)
                && Objects.equals(updatedAt, other.updatedAt);
    }

    @Override
    public String toString() {
        return "Products [id=" + id + ", sku=" + sku + ", productName=" + productName + ", shortDescription="
                + shortDescription + ", description=" + description + ", brandId=" + brandId + ", categoryId="
                + categoryId + ", price=" + price + ", isAvailable=" + isAvailable + ", createdAt=" + createdAt
                + ", updatedAt=" + updatedAt + "]";
    }
}
