package com.dto;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;

public class PaymentInitiateRequest {
    @NotNull
    private Long orderId;
    private String provider; // e.g., RAZORPAY (for fake flow you can use "FAKE")
    private BigDecimal amount;
    private Long userId; // set by server

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public String getProvider() { return provider; }
    public void setProvider(String provider) { this.provider = provider; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}
