package com.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "cart_items")
@Schema(name = "CartItem", description = "An item inside a shopping cart")
public class CartItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Primary key of the cart item", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    @JsonBackReference
    @Schema(description = "Reference to the parent cart", required = true, implementation = Carts.class)
    private Carts cart;

    @Column(name = "product_id", nullable = false)
    @Schema(description = "Identifier of the product added to cart", example = "101", required = true)
    private Long productId;

    @Column(nullable = false)
    @Schema(description = "Quantity of the product in the cart", example = "2", required = true)
    private Integer quantity;

    @Column(name = "unit_price", precision = 12, scale = 2)
    @Schema(description = "Unit price of the product at the time it was added", example = "499.99")
    private BigDecimal unitPrice;

    @CreationTimestamp
    @Column(name = "added_at", updatable = false)
    @Schema(description = "Timestamp when the item was added to the cart", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime addedAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @Schema(description = "Timestamp when the item was last updated", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;

    public CartItems() {}

    public CartItems(Long productId, Integer quantity, BigDecimal unitPrice) {
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Carts getCart() { return cart; }
    public void setCart(Carts cart) { this.cart = cart; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }

    public LocalDateTime getAddedAt() { return addedAt; }
    public void setAddedAt(LocalDateTime addedAt) { this.addedAt = addedAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartItems)) return false;
        CartItems that = (CartItems) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "CartItems{" +
                "id=" + id +
                ", cart=" + (cart != null ? cart.getId() : null) +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", addedAt=" + addedAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
