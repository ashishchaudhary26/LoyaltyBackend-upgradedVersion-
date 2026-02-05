package com.controller;

import com.dto.OrderResponse;
import com.dto.OrderStatusChangeResponse;
import com.entity.Notification;
import com.entity.Orders;
import com.enums.OrderStatus;
import com.repository.NotificationRepository;
import com.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationRepository repository;

    @GetMapping
    public List<Notification> getUserNotifications(@RequestHeader("X-USER-ID") Long userId) {
        return repository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    @GetMapping("/unread-count")
    public long getUnreadCount(@RequestHeader("X-USER-ID") Long userId) {
        return repository.countByUserIdAndIsReadFalse(userId);
    }

    @PutMapping("/{id}/read")
    public void markAsRead(@PathVariable Long id) {
        Notification n = repository.findById(id).orElseThrow();
        n.setRead(true);
        repository.save(n);
    }
}
