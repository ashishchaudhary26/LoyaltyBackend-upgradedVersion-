package com.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "payments")
@Schema(name = "Payment", description = "Payment information for an order")
public class Payments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Primary key of the payment record", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    /**
     * Use JsonBackReference to avoid infinite recursion if Orders has a reference to Payments.
     * If Orders does not include payments as a nested object in API responses, this is harmless.
     */
    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    @JsonBackReference
    @JsonIgnoreProperties({"orderItems", "shippingAddress", "payment"}) // adjust if Orders has these properties
    @Schema(description = "Associated order for this payment", required = true, implementation = Orders.class)
    private Orders order;

    @Column(length = 100)
    @Schema(description = "Payment provider name", example = "Razorpay")
    private String provider;

    @Column(name = "provider_payment_id", length = 255)
    @Schema(description = "Provider's payment identifier or transaction id", example = "pay_01G9KJ2X")
    private String providerPaymentId;

    @Column(precision = 12, scale = 2)
    @Schema(description = "Amount paid", example = "199.99")
    private BigDecimal amount;

    @Column(name = "payment_status", length = 50)
    @Schema(description = "Status of the payment", example = "COMPLETED")
    private String paymentStatus;

    @Column(name = "created_at")
    @Schema(description = "Record creation timestamp", example = "2025-11-25T12:34:56", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    // --- getters / setters ---

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

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getProviderPaymentId() {
        return providerPaymentId;
    }

    public void setProviderPaymentId(String providerPaymentId) {
        this.providerPaymentId = providerPaymentId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // --- equals / hashCode / toString ---

    @Override
    public int hashCode() {
        return Objects.hash(amount, createdAt, id, order != null ? order.getId() : null, paymentStatus, provider, providerPaymentId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Payments other = (Payments) obj;
        return Objects.equals(amount, other.amount)
                && Objects.equals(createdAt, other.createdAt)
                && Objects.equals(id, other.id)
                && Objects.equals(order != null ? order.getId() : null, other.order != null ? other.order.getId() : null)
                && Objects.equals(paymentStatus, other.paymentStatus)
                && Objects.equals(provider, other.provider)
                && Objects.equals(providerPaymentId, other.providerPaymentId);
    }

    @Override
    public String toString() {
        return "Payments [id=" + id +
                ", orderId=" + (order != null ? order.getId() : null) +
                ", provider=" + provider +
                ", providerPaymentId=" + providerPaymentId +
                ", amount=" + amount +
                ", paymentStatus=" + paymentStatus +
                ", createdAt=" + createdAt +
                "]";
    }
}
