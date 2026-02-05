package com.dto;

public class PaymentInitiateResponse {
    private Long orderId;
    private String provider;
    private String paymentReference; // fake token / url / id returned

    public PaymentInitiateResponse() {}

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public String getProvider() { return provider; }
    public void setProvider(String provider) { this.provider = provider; }

    public String getPaymentReference() { return paymentReference; }
    public void setPaymentReference(String paymentReference) { this.paymentReference = paymentReference; }
}
