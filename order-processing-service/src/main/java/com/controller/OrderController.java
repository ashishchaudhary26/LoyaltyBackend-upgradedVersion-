package com.controller;

import com.dto.*;
import com.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Orders", description = "Order and payment endpoints")
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // ---------------- Authenticated User Endpoints ----------------

    @Operation(summary = "Create order from cart (authenticated user)")
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @RequestHeader("X-USER-ID") Long userId,
            @Valid @RequestBody CreateOrderRequest req) {

        req.setUserId(userId); // enforce server-side user id

        System.out.println("DEBUG /api/v1/orders createOrder: userId=" + userId
                + ", shippingAddressId=" + req.getShippingAddressId()
                + ", itemsCount=" + (req.getItems() != null ? req.getItems().size() : 0));

        OrderResponse resp = orderService.createOrder(req);
        return ResponseEntity.status(201).body(resp);
    }

    @Operation(summary = "List current user's orders")
    @GetMapping
    public ResponseEntity<List<OrderResponse>> listMyOrders(@RequestHeader("X-USER-ID") Long userId) {
        List<OrderResponse> list = orderService.listOrdersByUser(userId);
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Get order details by order number (owner or admin can view)")
    @GetMapping("/{orderNumber}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable String orderNumber,
            @RequestHeader("X-USER-ID") Long userId) {
        // Optionally, service can validate that userId is owner or admin
        OrderResponse resp = orderService.getOrderByNumber(orderNumber);
        return ResponseEntity.ok(resp);
    }

}
