package com.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "product_images")
@Schema(name = "ProductImage", description = "Represents an image associated with a product")
public class Product_Images {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique ID of the image", example = "1",
            accessMode = Schema.AccessMode.READ_ONLY)
    public Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    @Schema(description = "The product this image belongs to")
    public Products product;

    @Column(name = "image_key", length = 512)
    @Schema(description = "Internal image key (e.g., S3 key)", example = "products/iphone15/front.jpg")
    public String imageKey;

    @Column(name = "image_url", length = 1000)
    @Schema(description = "Public URL of the product image",
            example = "https://cdn.mystore.com/products/iphone15/front.jpg")
    public String imageUrl;

    @Column(name = "alt_text", length = 255)
    @Schema(description = "Alternative text for the image", example = "Front view of iPhone 15 Pro")
    public String altText;

    @Column(name = "sort_order")
    @Schema(description = "Order index for displaying multiple images", example = "1")
    public Integer sortOrder;

    @Column(name = "created_at")
    @Schema(description = "Timestamp when the image was added",
            example = "2025-11-25T10:20:30")
    public LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public String getImageKey() {
        return imageKey;
    }

    public void setImageKey(String imageKey) {
        this.imageKey = imageKey;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAltText() {
        return altText;
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public int hashCode() {
        return Objects.hash(altText, createdAt, id, imageKey, imageUrl, product, sortOrder);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Product_Images other = (Product_Images) obj;
        return Objects.equals(altText, other.altText)
                && Objects.equals(createdAt, other.createdAt)
                && Objects.equals(id, other.id)
                && Objects.equals(imageKey, other.imageKey)
                && Objects.equals(imageUrl, other.imageUrl)
                && Objects.equals(product, other.product)
                && Objects.equals(sortOrder, other.sortOrder);
    }

    @Override
    public String toString() {
        return "Product_Images [id=" + id + ", product=" + product + ", imageKey=" + imageKey + ", imageUrl=" + imageUrl
                + ", altText=" + altText + ", sortOrder=" + sortOrder + ", createdAt=" + createdAt + "]";
    }
}
