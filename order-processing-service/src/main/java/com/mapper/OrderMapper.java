package com.mapper;

import com.dto.*;
import com.entity.*;
import com.enums.OrderStatus;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderItemDto toDto(OrderItems it) {
        OrderItemDto dto = new OrderItemDto();
        dto.setProductId(it.getProductId());
        dto.setQuantity(it.getQuantity());
        // unitPrice not stored in OrderItems entity — frontend supplies; if you store,
        // map here
        return dto;
    }

    public static OrderResponse toResponse(Orders o) {
        OrderResponse r = new OrderResponse();
        r.setId(o.getId());
        r.setOrderNumber(o.getOrderNumber());
        r.setUserId(o.getUserId());
        // r.setOrderStatus(o.getOrderStatus());
        r.setOrderStatus(o.getStatus().name());

        r.setTotalAmount(o.getTotalAmount());
        r.setShippingAddressId(o.getShippingAddress() != null ? o.getShippingAddress().getId() : null);
        if (o.getOrderItems() != null) {
            List<OrderItemDto> items = o.getOrderItems().stream().map(OrderMapper::toDto).collect(Collectors.toList());
            r.setItems(items);
        }
        r.setCreatedAt(o.getCreatedAt());
        r.setUpdatedAt(o.getUpdatedAt());
        r.setCustomerName(o.getCustomerName());

        return r;
    }

    public static Orders fromCreateRequest(CreateOrderRequest req) {
        Orders o = new Orders();
        o.setUserId(req.getUserId());
        o.setCartUuid(req.getCartUuid());
        // o.setOrderStatus("PENDING");
        o.setStatus(OrderStatus.PROCESSING);

        o.setCustomerName(req.getCustomerName());

        return o;
    }

    public static OrderItems toOrderItemEntity(OrderItemDto dto) {
        OrderItems it = new OrderItems();
        it.setProductId(dto.getProductId());
        it.setQuantity(dto.getQuantity());
        return it;
    }
}
