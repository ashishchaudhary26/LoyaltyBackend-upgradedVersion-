package com.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "orders")
@Schema(name = "Order", description = "Order placed by a user containing items, shipping address and payment information")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Primary key of the order", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Column(name = "order_number", nullable = false, length = 64)
    @Schema(description = "Unique order number", example = "ORD-20251234", required = true)
    private String orderNumber;

    @Column(name = "user_id", nullable = false)
    @Schema(description = "Identifier of the user who placed the order", example = "42", required = true)
    private Long userId;

    @Column(name = "cart_uuid", length = 36)
    @Schema(description = "UUID of the cart from which the order was created", example = "550e8400-e29b-41d4-a716-446655440000")
    private String cartUuid;

    @Column(name = "total_amount", nullable = false, precision = 12, scale = 2)
    @Schema(description = "Total payable amount for the order", example = "199.99", required = true)
    private BigDecimal totalAmount;

    // @Column(name = "order_status", length = 50)
    // @Schema(description = "Current status of the order", example = "PENDING")
    // private String orderStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatus status;

    /**
     * Many orders may reference the same shipping address (address reuse).
     * Use JsonIgnoreProperties to avoid serializing unnecessary nested order lists.
     */
    @ManyToOne
    @JoinColumn(name = "shipping_address_id")
    @JsonIgnoreProperties({ "orders" }) // if ShippingAddresses has orders collection
    @Schema(description = "Shipping address associated with the order")
    private ShippingAddresses shippingAddress;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @Schema(description = "List of items in this order", implementation = OrderItems.class)
    private List<OrderItems> orderItems;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @Schema(description = "Payment record associated with the order", implementation = Payments.class)
    private Payments payment;

    @Column(name = "payment_reference", length = 255)
    @Schema(description = "Payment provider reference or transaction id", example = "pay_abc123")
    private String paymentReference;

    @Column(name = "created_at", updatable = false)
    @Schema(description = "Order creation timestamp", example = "2025-11-25T12:34:56", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @Schema(description = "Last update timestamp for the order", example = "2025-11-25T12:45:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;

    @Column(name = "customer_name")
    private String customerName;

    // new feature for reward calculation
    private Double rewardEarned = 0.0;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCartUuid() {
        return cartUuid;
    }

    public void setCartUuid(String cartUuid) {
        this.cartUuid = cartUuid;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    // public String getOrderStatus() {
    // return orderStatus;
    // }

    // public void setOrderStatus(String orderStatus) {
    // this.orderStatus = orderStatus;
    // }

    public ShippingAddresses getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(ShippingAddresses shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public List<OrderItems> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItems> orderItems) {
        this.orderItems = orderItems;
    }

    public Payments getPayment() {
        return payment;
    }

    public OrderStatus getStatus() {
        return status;
    }

    // public void setStatus(OrderStatus status) {
    // this.status = status;
    // }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setPayment(Payments payment) {
        this.payment = payment;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public void setPaymentReference(String paymentReference) {
        this.paymentReference = paymentReference;
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

    public Double getRewardEarned() {
        return rewardEarned;
    }

    public void setRewardEarned(Double rewardEarned) {
        this.rewardEarned = rewardEarned;
    }

    // @Override
    // public String toString() {
    // return "Orders [id=" + id + ", orderNumber=" + orderNumber + ", userId=" +
    // userId + ", cartUuid=" + cartUuid
    // + ", totalAmount=" + totalAmount + ", orderStatus=" + orderStatus + ",
    // paymentReference="
    // + paymentReference + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
    // + "]";
    // }
}
