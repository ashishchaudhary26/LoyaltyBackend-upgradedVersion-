package com.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "product_stock")
@Schema(name = "ProductStock", description = "Represents stock and inventory information for a product")
public class Product_Stock {

    @Id
    @Column(name = "product_id")
    @Schema(description = "ID of the product (also acts as primary key)", example = "101")
    public Long productId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "product_id")
    @Schema(description = "Reference to the product this stock belongs to")
    public Products product;

    @Column(name = "available_quantity")
    @Schema(description = "Total available quantity in stock", example = "150")
    public Integer availableQuantity;

    @Column(name = "reserved_quantity")
    @Schema(description = "Quantity reserved for pending orders", example = "10")
    public Integer reservedQuantity;

    @Column(name = "updated_at")
    @Schema(description = "Timestamp when the stock was last updated", example = "2025-11-25T10:20:30")
    public LocalDateTime updatedAt;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public Integer getReservedQuantity() {
        return reservedQuantity;
    }

    public void setReservedQuantity(Integer reservedQuantity) {
        this.reservedQuantity = reservedQuantity;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public int hashCode() {
        return Objects.hash(availableQuantity, product, productId, reservedQuantity, updatedAt);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Product_Stock other = (Product_Stock) obj;
        return Objects.equals(availableQuantity, other.availableQuantity)
                && Objects.equals(product, other.product)
                && Objects.equals(productId, other.productId)
                && Objects.equals(reservedQuantity, other.reservedQuantity)
                && Objects.equals(updatedAt, other.updatedAt);
    }

    @Override
    public String toString() {
        return "Product_Stock [productId=" + productId + ", product=" + product + ", availableQuantity="
                + availableQuantity + ", reservedQuantity=" + reservedQuantity + ", updatedAt=" + updatedAt + "]";
    }
}
