package com.entity;

import java.util.Objects;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "order_items")
@Schema(name = "OrderItem", description = "Representation of a single item within an order")
public class OrderItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Primary key of the order item", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @JsonBackReference
    @Schema(description = "Reference to the parent order", required = true)
    private Orders order;

    @Column(name = "product_id", nullable = false)
    @Schema(description = "Identifier of the product", example = "101", required = true)
    private Long productId;

    @Column(nullable = false)
    @Schema(description = "Quantity of the product ordered", example = "2", required = true)
    private Integer quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, order, productId, quantity);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OrderItems other = (OrderItems) obj;
        return Objects.equals(id, other.id) && Objects.equals(order, other.order)
                && Objects.equals(productId, other.productId) && Objects.equals(quantity, other.quantity);
    }

    @Override
    public String toString() {
        return "OrderItems [id=" + id + ", orderId=" + (order != null ? order.getId() : null) + ", productId=" + productId + ", quantity=" + quantity
                + "]";
    }

}
