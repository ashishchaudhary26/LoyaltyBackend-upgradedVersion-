package com.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "carts")
@Schema(name = "Cart", description = "Shopping cart containing items for a user")
public class Carts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Primary key of the cart", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Column(name = "cart_uuid", length = 36, nullable = false, unique = true)
    @Schema(description = "UUID of the cart (used to identify cart across sessions)", example = "550e8400-e29b-41d4-a716-446655440000", required = true)
    private String cartUuid;

    @Column(name = "user_id")
    @Schema(description = "Identifier of the user who owns the cart", example = "42")
    private Long userId;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    @Schema(description = "Timestamp when the cart was created", example = "2025-11-25T12:34:56", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @Schema(description = "Timestamp when the cart was last updated", example = "2025-11-25T12:45:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @Schema(description = "List of items in the cart", implementation = CartItems.class)
    private List<CartItems> items = new ArrayList<>();

    public Carts() {}

    public Carts(String cartUuid, Long userId) {
        this.cartUuid = cartUuid;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCartUuid() {
        return cartUuid;
    }

    public void setCartUuid(String cartUuid) {
        this.cartUuid = cartUuid;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public List<CartItems> getItems() {
        return items;
    }

    public void setItems(List<CartItems> items) {
        this.items = items;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartUuid, createdAt, id, updatedAt, userId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Carts other = (Carts) obj;
        return Objects.equals(cartUuid, other.cartUuid) && Objects.equals(createdAt, other.createdAt)
                && Objects.equals(id, other.id) && Objects.equals(updatedAt, other.updatedAt)
                && Objects.equals(userId, other.userId);
    }

    @Override
    public String toString() {
        return "Carts [id=" + id + ", cartUuid=" + cartUuid + ", userId=" + userId + ", createdAt=" + createdAt
                + ", updatedAt=" + updatedAt + "]";
    }
}
