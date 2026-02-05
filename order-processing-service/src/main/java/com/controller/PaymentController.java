package com.controller;

import com.dto.*;
import com.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
@Tag(name = "Payments", description = "Payment endpoints")
public class PaymentController {

    private final OrderService orderService;

    public PaymentController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/initiate")
    public ResponseEntity<PaymentInitiateResponse> initiatePayment(
            @RequestHeader("X-USER-ID") Long userId,
            @Valid @RequestBody PaymentInitiateRequest req) {

        req.setUserId(userId);
        return ResponseEntity.status(201).body(orderService.initiatePayment(req));
    }

    @PostMapping("/verify")
    public ResponseEntity<Void> verifyPayment(@Valid @RequestBody PaymentVerifyRequest req) {
        orderService.handlePaymentCallback(
                req.getOrderId(),
                req.getProviderPaymentId(),
                req.getStatus());
        return ResponseEntity.noContent().build();
    }
}
