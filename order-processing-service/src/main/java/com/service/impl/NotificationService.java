package com.service.impl;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.entity.Notification;
import com.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository repository;
    private final SimpMessagingTemplate messagingTemplate;

    public void sendOrderStatusNotification(Long userId, String orderId, String status) {

        String message = "Your order #" + orderId + " is " + status;

        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setTitle("Order Update");
        notification.setMessage(message);

        repository.save(notification);

        // Push to frontend
        messagingTemplate.convertAndSend(
                "/topic/notifications/" + userId,
                notification);
    }
}
