package com.dto;

public class PaymentVerifyRequest {
    private Long orderId;
    private String providerPaymentId;
    private String status; // COMPLETED | FAILED

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public String getProviderPaymentId() { return providerPaymentId; }
    public void setProviderPaymentId(String providerPaymentId) { this.providerPaymentId = providerPaymentId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
