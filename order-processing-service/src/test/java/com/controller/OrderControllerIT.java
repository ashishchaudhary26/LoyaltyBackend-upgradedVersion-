package com.controller;

import com.dto.*;
import com.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = true)
public class OrderControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private OrderService orderService;

    private OrderResponse sampleOrderResponse() {
        OrderResponse r = new OrderResponse();
        r.setId(1L);
        r.setOrderNumber("ORD-20251125-ABC12345");
        r.setUserId(10L);
        r.setOrderStatus("PENDING");
        r.setTotalAmount(new BigDecimal("1498.00"));
        r.setShippingAddressId(12L);
        OrderItemDto it1 = new OrderItemDto();
        it1.setProductId(101L);
        it1.setQuantity(2);
        it1.setUnitPrice(new BigDecimal("499.00"));
        OrderItemDto it2 = new OrderItemDto();
        it2.setProductId(205L);
        it2.setQuantity(1);
        it2.setUnitPrice(new BigDecimal("500.00"));
        r.setItems(List.of(it1, it2));
        r.setCreatedAt(LocalDateTime.now());
        r.setUpdatedAt(LocalDateTime.now());
        return r;
    }

    @Test
    @DisplayName("POST /api/v1/orders -> 201 Created")
    @WithMockUser(username = "10") // principal name = userId used by controller
    public void createOrder_shouldReturn201() throws Exception {
        CreateOrderRequest req = new CreateOrderRequest();
        OrderItemDto it = new OrderItemDto();
        it.setProductId(101L);
        it.setQuantity(2);
        it.setUnitPrice(new BigDecimal("499.00"));
        req.setItems(List.of(it));
        req.setShippingAddressId(12L);

        OrderResponse resp = sampleOrderResponse();

        Mockito.when(orderService.createOrder(any(CreateOrderRequest.class))).thenReturn(resp);

        mockMvc.perform(post("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req))
                .principal(() -> "10")) // sets Principal.getName() = "10"
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.orderNumber").value(resp.getOrderNumber()))
                .andExpect(jsonPath("$.userId").value(resp.getUserId()));

    }

    @Test
    @DisplayName("GET /api/v1/orders -> 200 OK (list my orders)")
    public void listMyOrders_shouldReturn200() throws Exception {
        OrderResponse resp = sampleOrderResponse();
        Mockito.when(orderService.listOrdersByUser(eq(10L))).thenReturn(List.of(resp));

        mockMvc.perform(get("/api/v1/orders")
                .principal(() -> "10")) // <-- manually set Principal
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].orderNumber").value(resp.getOrderNumber()));
    }

    @Test
    @DisplayName("GET /api/v1/orders/{orderNumber} -> 200 OK")
    @WithMockUser(username = "10")
    public void getOrderByNumber_shouldReturn200() throws Exception {
        OrderResponse resp = sampleOrderResponse();
        Mockito.when(orderService.getOrderByNumber(eq(resp.getOrderNumber()))).thenReturn(resp);

        mockMvc.perform(get("/api/v1/orders/{orderNumber}", resp.getOrderNumber()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderNumber").value(resp.getOrderNumber()))
                .andExpect(jsonPath("$.totalAmount").exists());
    }

    @Test
    @DisplayName("POST /api/v1/payments/initiate -> 201 Created")
    @WithMockUser(username = "10")
    public void initiatePayment_shouldReturn201() throws Exception {
        PaymentInitiateRequest req = new PaymentInitiateRequest();
        req.setOrderId(1L);
        req.setProvider("FAKE");
        req.setAmount(new BigDecimal("1498.00"));

        PaymentInitiateResponse resp = new PaymentInitiateResponse();
        resp.setOrderId(1L);
        resp.setProvider("FAKE");
        resp.setPaymentReference("PAY-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());

        Mockito.when(orderService.initiatePayment(any(PaymentInitiateRequest.class))).thenReturn(resp);

        mockMvc.perform(post("/api/v1/payments/initiate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req))
                .principal(() -> "10")) // sets Principal.getName() = "10"
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.paymentReference").value(resp.getPaymentReference()));

    }

    @Test
    @DisplayName("POST /api/v1/payments/verify -> 204 No Content")
    public void verifyPayment_shouldReturn204() throws Exception {
        PaymentVerifyRequest req = new PaymentVerifyRequest();
        req.setOrderId(1L);
        req.setProviderPaymentId("pay_01G9KJ2X");
        req.setStatus("COMPLETED");

        // This endpoint doesn't return content; just ensure controller calls service
        // (no exception)
        mockMvc.perform(post("/api/v1/payments/verify")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("PUT /api/v1/orders/{orderNumber}/status -> 200 OK (admin)")
    @WithMockUser(username = "1", roles = { "ADMIN" })
    public void updateOrderStatus_asAdmin_shouldReturn200() throws Exception {
        OrderResponse updated = sampleOrderResponse();
        updated.setOrderStatus("SHIPPED");
        updated.setUpdatedAt(LocalDateTime.now());

        // Mockito.when(orderService.updateOrderStatus(eq(updated.getOrderNumber()),
        // eq("SHIPPED")))
        // .thenReturn(updated);

        mockMvc.perform(put("/api/v1/admin/orders/{orderNumber}/status", updated.getOrderNumber())
                .param("status", "SHIPPED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.newStatus").value("SHIPPED"));
    }
}
