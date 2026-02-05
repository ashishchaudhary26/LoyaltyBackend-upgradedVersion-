package com.service;

import com.dto.*;
import com.enums.OrderStatus;

import java.util.List;

public interface OrderService {

    OrderResponse createOrder(CreateOrderRequest req);

    List<OrderResponse> listOrdersByUser(Long userId);

    OrderResponse getOrderByNumber(String orderNumber);

    OrderResponse updateOrderStatus(String orderNumber, OrderStatus status);

    PaymentInitiateResponse initiatePayment(PaymentInitiateRequest req);

    void handlePaymentCallback(Long orderId, String providerPaymentId, String paymentStatus);

    List<OrderResponse> getAllOrders();

    void deleteOrder(String orderNumber);
}
