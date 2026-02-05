package com.controller;

import com.dto.OrderResponse;
import com.dto.OrderStatusChangeResponse;
import com.entity.Orders;
import com.enums.OrderStatus;
import com.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Admin Orders", description = "Admin management of orders")
@RestController
@RequestMapping("/api/v1/admin/orders") // change
public class AdminOrderController {

    private final OrderService orderService;

    public AdminOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "Admin: get all orders")
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @Operation(summary = "Admin: update order status")

    @PutMapping("/{orderNumber}/status/{status}")
    public ResponseEntity<?> updateStatus(
            @PathVariable String orderNumber,
            @PathVariable OrderStatus status) {

        OrderResponse updated = orderService.updateOrderStatus(orderNumber, status);

        OrderStatusChangeResponse resp = new OrderStatusChangeResponse();
        // resp.setOldStatus(null); // update

        resp.setNewStatus(updated.getOrderStatus());
        resp.setChangedAt(updated.getUpdatedAt());

        return ResponseEntity.ok(resp);
    }

    @Operation(summary = "Admin: delete order")
    @DeleteMapping("/{orderNumber}")
    public ResponseEntity<Void> deleteOrder(@PathVariable String orderNumber) {
        orderService.deleteOrder(orderNumber);
        return ResponseEntity.noContent().build();
    }
}
