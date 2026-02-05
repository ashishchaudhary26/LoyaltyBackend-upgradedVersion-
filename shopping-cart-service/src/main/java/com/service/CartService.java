package com.service;

import com.dto.AddCartItemRequest;
import com.dto.CartDto;
import com.dto.CartItemDto;
import com.dto.UpdateCartItemRequest;

public interface CartService {

    CartDto getCartByUuid(String cartUuid);

    CartDto getCartByUserId(Long userId);

    CartDto createCartIfNotExists(String cartUuid, Long userId);

    CartItemDto addItem(AddCartItemRequest req);

    CartItemDto updateItem(Long itemId, UpdateCartItemRequest req);

    void removeItem(Long itemId); // guest cart / default
    void removeItem(Long userId, Long itemId); // authenticated user

    void clearCart(String cartUuid); // guest cart / default
    void clearCartByUserId(Long userId); // authenticated user
}
