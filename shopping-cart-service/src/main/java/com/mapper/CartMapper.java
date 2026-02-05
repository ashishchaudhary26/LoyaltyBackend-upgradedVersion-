package com.mapper;

import com.dto.CartDto;
import com.dto.CartItemDto;
import com.entity.CartItems;
import com.entity.Carts;

import java.util.List;
import java.util.stream.Collectors;

public class CartMapper {

    public static CartItemDto toDto(CartItems item) {
        if (item == null) return null;
        return new CartItemDto(
                item.getId(),
                item.getProductId(),
                item.getQuantity(),
                item.getUnitPrice(),
                item.getAddedAt(),
                item.getUpdatedAt()
        );
    }

    public static List<CartItemDto> toItemDtoList(List<CartItems> items) {
        return items == null ? null : items.stream().map(CartMapper::toDto).collect(Collectors.toList());
    }

    public static CartDto toDto(Carts cart) {
        if (cart == null) return null;
        return new CartDto(
                cart.getId(),
                cart.getCartUuid(),
                cart.getUserId(),
                toItemDtoList(cart.getItems()),
                cart.getCreatedAt(),
                cart.getUpdatedAt()
        );
    }
}
