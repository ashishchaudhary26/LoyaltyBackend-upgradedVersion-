package com.controller;

import com.dto.AddCartItemRequest;
import com.dto.CartDto;
import com.dto.CartItemDto;
import com.dto.UpdateCartItemRequest;
import com.service.CartService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import static org.mockito.Mockito.any;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CartController.class)
public class CartControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @Autowired
    private ObjectMapper objectMapper;

    private CartItemDto sampleCartItemDto() {
        return new CartItemDto(
                10L,
                101L,
                2,
                new BigDecimal("499.99"),
                LocalDateTime.parse("2025-11-25T12:34:56"),
                LocalDateTime.parse("2025-11-25T12:45:00")
        );
    }

    private CartDto sampleCartDto() {
        return new CartDto(
                1L,
                "550e8400-e29b-41d4-a716-446655440000",
                42L,
                List.of(sampleCartItemDto()),
                LocalDateTime.parse("2025-11-25T12:00:00"),
                LocalDateTime.parse("2025-11-25T12:45:00")
        );
    }

    @Test
    @DisplayName("GET /api/v1/cart?cart_uuid=... -> 200 OK")
    public void getCart_ByUuid_Returns200() throws Exception {
        CartDto dto = sampleCartDto();
        Mockito.when(cartService.getCartByUuid(eq(dto.getCartUuid()))).thenReturn(dto);

        mockMvc.perform(get("/api/v1/cart")
                .param("cart_uuid", dto.getCartUuid())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(dto.getId()))
                .andExpect(jsonPath("$.cartUuid").value(dto.getCartUuid()))
                .andExpect(jsonPath("$.userId").value(dto.getUserId()))
                .andExpect(jsonPath("$.items", hasSize(1)))
                .andExpect(jsonPath("$.items[0].productId").value(101));
    }

    @Test
    @DisplayName("GET /api/v1/cart?user_id=... -> 200 OK")
    public void getCart_ByUserId_Returns200() throws Exception {
        CartDto dto = sampleCartDto();
        Mockito.when(cartService.getCartByUserId(eq(dto.getUserId()))).thenReturn(dto);

        mockMvc.perform(get("/api/v1/cart")
                .param("user_id", dto.getUserId().toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(dto.getId()))
                .andExpect(jsonPath("$.items", hasSize(1)));
    }

    @Test
    @DisplayName("GET /api/v1/cart -> 400 Bad Request when neither param provided")
    public void getCart_NoParams_Returns400() throws Exception {
        mockMvc.perform(get("/api/v1/cart"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/v1/cart?cart_uuid=... -> 404 when cart not found")
    public void getCart_NotFound_Returns404() throws Exception {
        String uuid = "non-existent-uuid";
        Mockito.when(cartService.getCartByUuid(eq(uuid))).thenReturn(null);

        mockMvc.perform(get("/api/v1/cart")
                .param("cart_uuid", uuid))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/v1/cart/items -> 201 Created")
    public void addItem_Returns201() throws Exception {
        AddCartItemRequest req = new AddCartItemRequest();
        req.setCartUuid("550e8400-e29b-41d4-a716-446655440000");
        req.setProductId(101L);
        req.setQuantity(2);
        req.setUnitPrice(new BigDecimal("499.99"));

        CartItemDto created = sampleCartItemDto();
        Mockito.when(cartService.addItem(any(AddCartItemRequest.class))).thenReturn(created);

        mockMvc.perform(post("/api/v1/cart/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(created.getId()))
                .andExpect(jsonPath("$.productId").value(created.getProductId()))
                .andExpect(jsonPath("$.quantity").value(created.getQuantity()));
    }

    @Test
    @DisplayName("POST /api/v1/cart/items -> 400 Bad Request when validation fails")
    public void addItem_InvalidRequest_Returns400() throws Exception {
        AddCartItemRequest req = new AddCartItemRequest();
        req.setCartUuid("550e8400-e29b-41d4-a716-446655440000");

        mockMvc.perform(post("/api/v1/cart/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT /api/v1/cart/items/{itemId} -> 200 OK")
    public void updateItem_Returns200() throws Exception {
        Long itemId = 10L;
        UpdateCartItemRequest req = new UpdateCartItemRequest(3);

        CartItemDto updated = new CartItemDto(
                itemId,
                101L,
                3,
                new BigDecimal("499.99"),
                LocalDateTime.parse("2025-11-25T12:34:56"),
                LocalDateTime.parse("2025-11-25T13:00:00")
        );

        Mockito.when(cartService.updateItem(eq(itemId), any(UpdateCartItemRequest.class))).thenReturn(updated);

        mockMvc.perform(put("/api/v1/cart/items/{itemId}", itemId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(itemId))
                .andExpect(jsonPath("$.quantity").value(3));
    }

    @Test
    @DisplayName("DELETE /api/v1/cart/items/{itemId} -> 204 No Content")
    public void removeItem_Returns204() throws Exception {
        Long itemId = 10L;
        Mockito.doNothing().when(cartService).removeItem(eq(itemId));

        mockMvc.perform(delete("/api/v1/cart/items/{itemId}", itemId))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /api/v1/cart?cart_uuid=... -> 204 No Content")
    public void clearCart_Returns204() throws Exception {
        String uuid = "550e8400-e29b-41d4-a716-446655440000";
        Mockito.doNothing().when(cartService).clearCart(eq(uuid));

        mockMvc.perform(delete("/api/v1/cart")
                .param("cart_uuid", uuid))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /api/v1/cart -> 400 Bad Request when cart_uuid missing")
    public void clearCart_MissingParam_Returns400() throws Exception {
        mockMvc.perform(delete("/api/v1/cart"))
                .andExpect(status().isBadRequest());
    }
}
