package com.service.impl;

import com.client.ProductClient;
import com.dto.*;
import com.entity.*;
import com.enums.OrderStatus;
import com.mapper.OrderMapper;
import com.repository.*;
import com.service.OrderService;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrdersRepository ordersRepository;
    private final OrderItemsRepository orderItemsRepository;
    private final PaymentsRepository paymentsRepository;
    private final ShippingAddressesRepository shippingAddressesRepository;
    private final NotificationService notificationService;

    // private final SqsPublisher sqsPublisher;
    private final ProductClient productClient;

    public OrderServiceImpl(
            NotificationService notificationService,
            OrdersRepository ordersRepository,
            OrderItemsRepository orderItemsRepository,
            PaymentsRepository paymentsRepository,
            ShippingAddressesRepository shippingAddressesRepository,
            ProductClient productClient) {
        this.notificationService = notificationService;
        this.ordersRepository = ordersRepository;
        this.orderItemsRepository = orderItemsRepository;
        this.paymentsRepository = paymentsRepository;
        this.shippingAddressesRepository = shippingAddressesRepository;
        this.productClient = productClient;
    }

    @Override
    public OrderResponse createOrder(CreateOrderRequest req) {
        System.out.println(" Incoming CreateOrderRequest:");
        System.out.println("   userId=" + req.getUserId() +
                ", shippingAddressId=" + req.getShippingAddressId());
        if (req.getItems() != null) {
            for (OrderItemDto it : req.getItems()) {
                System.out.println("   item -> productId=" + it.getProductId() +
                        ", qty=" + it.getQuantity() +
                        ", unitPrice=" + it.getUnitPrice());
            }
        }

        try {
            // 1) Validate & load shipping address
            ShippingAddresses addr = shippingAddressesRepository
                    .findById(req.getShippingAddressId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, "Shipping address not found"));

            // (optional but recommended) ensure address belongs to this user
            if (!Objects.equals(addr.getUserId(), req.getUserId())) {
                throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN,
                        "Shipping address does not belong to this user");
            }

            // 2) Validate items and stock
            BigDecimal total = BigDecimal.ZERO;

            for (OrderItemDto it : req.getItems()) {
                if (it.getQuantity() == null || it.getQuantity() <= 0) {
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,
                            "Invalid quantity for product: " + it.getProductId());
                }

                ProductStockDto stockDto;
                try {
                    stockDto = productClient.getStock(it.getProductId());
                } catch (FeignException.NotFound ex) {
                    System.out.println(" Stock not found in product-service for product " + it.getProductId());
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,
                            "Product stock not found: " + it.getProductId());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    throw new ResponseStatusException(
                            HttpStatus.SERVICE_UNAVAILABLE,
                            "Stock service unavailable for product: " + it.getProductId());
                }

                Integer available = stockDto == null ? 0 : stockDto.getAvailableQuantity();
                System.out.println("   Stock for product " + it.getProductId() + " = " + available);

                if (available == null || available < it.getQuantity()) {
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,
                            "Insufficient stock for product: " + it.getProductId());
                }

                try {
                    productClient.reserve(it.getProductId(), it.getQuantity());
                    System.out.println("   Reserved stock for product " + it.getProductId());
                } catch (FeignException.Conflict ex) {
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT,
                            "Unable to reserve stock for product: " + it.getProductId());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    throw new ResponseStatusException(
                            HttpStatus.SERVICE_UNAVAILABLE,
                            "Stock service unavailable while reserving product: " + it.getProductId());
                }

                BigDecimal unit = it.getUnitPrice() == null
                        ? BigDecimal.ZERO
                        : it.getUnitPrice();
                total = total.add(unit.multiply(BigDecimal.valueOf(it.getQuantity())));
            }

            // 3) Build Orders entity
            System.out.println("Creating Orders entity…");
            Orders order = OrderMapper.fromCreateRequest(req);

            order.setUserId(req.getUserId());
            order.setShippingAddress(addr); // use ManyToOne association
            order.setOrderNumber(generateOrderNumber());
            // order.setOrderStatus(
            // order.getOrderStatus() == null ? "PENDING_PAYMENT" : order.getOrderStatus());
            order.setStatus(
                    order.getStatus() == null ? OrderStatus.PENDING : order.getStatus());
            order.setTotalAmount(total);
            order.setCreatedAt(LocalDateTime.now());
            order.setUpdatedAt(LocalDateTime.now());
            order.setCustomerName(req.getCustomerName());

            System.out.println("   OrderNumber=" + order.getOrderNumber()
                    + ", total=" + order.getTotalAmount());

            // 4) Save order
            Orders savedOrder = ordersRepository.save(order);
            System.out.println(" Order saved with id=" + savedOrder.getId());

            // 5) Map and save order items
            List<OrderItems> entities = req.getItems().stream()
                    .map(OrderMapper::toOrderItemEntity)
                    .peek(e -> e.setOrder(savedOrder))
                    .collect(Collectors.toList());

            orderItemsRepository.saveAll(entities);
            System.out.println("Order items saved, count=" + entities.size());

            // 6) Return response
            OrderResponse resp = OrderMapper.toResponse(savedOrder);
            System.out.println(" Done createOrder, returning response with orderNumber=" + resp.getOrderNumber());
            return resp;

        } catch (ResponseStatusException ex) {
            System.out.println("⚠️ createOrder failed: " + ex.getStatus() + " - " + ex.getReason());
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to create order");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> listOrdersByUser(Long userId) {
        return ordersRepository.findAll().stream()
                .filter(o -> o.getUserId() != null && o.getUserId().equals(userId))
                .map(OrderMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrderByNumber(String orderNumber) {
        Orders o = ordersRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        return OrderMapper.toResponse(o);
    }

    @Override
    public PaymentInitiateResponse initiatePayment(PaymentInitiateRequest req) {
        Orders o = ordersRepository.findById(req.getOrderId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        Payments p = paymentsRepository.findByOrderId(o.getId()).orElse(new Payments());
        p.setOrder(o);
        p.setProvider(req.getProvider() == null ? "FAKE" : req.getProvider());
        p.setAmount(req.getAmount() == null ? o.getTotalAmount() : req.getAmount());
        p.setPaymentStatus("INITIATED");
        p.setCreatedAt(LocalDateTime.now());
        paymentsRepository.save(p);

        String fakeRef = "PAY-" + UUID.randomUUID().toString().substring(0, 12).toUpperCase();
        PaymentInitiateResponse resp = new PaymentInitiateResponse();
        resp.setOrderId(o.getId());
        resp.setProvider(p.getProvider());
        resp.setPaymentReference(fakeRef);
        return resp;
    }

    @Override
    public void handlePaymentCallback(Long orderId, String providerPaymentId, String paymentStatus) {
        Orders o = ordersRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        Payments p = paymentsRepository.findByOrderId(orderId).orElse(new Payments());
        p.setOrder(o);
        p.setProviderPaymentId(providerPaymentId);
        p.setPaymentStatus(paymentStatus);
        p.setCreatedAt(LocalDateTime.now());
        paymentsRepository.save(p);

        if ("COMPLETED".equalsIgnoreCase(paymentStatus)) {
            if (o.getOrderItems() != null) {
                for (OrderItems it : o.getOrderItems()) {
                    try {
                        productClient.commit(it.getProductId(), it.getQuantity());
                    } catch (Exception ignored) {
                    }
                }
            }
            // o.setOrderStatus("CONFIRMED");
            o.setStatus(OrderStatus.CONFIRMED);

            ordersRepository.save(o);
        } else {
            if (o.getOrderItems() != null) {
                for (OrderItems it : o.getOrderItems()) {
                    try {
                        productClient.release(it.getProductId(), it.getQuantity());
                    } catch (Exception ignored) {
                    }
                }
            }
            // o.setOrderStatus("PAYMENT_FAILED");
            o.setStatus(OrderStatus.FAILED);

            ordersRepository.save(o);
        }
    }

    private String generateOrderNumber() {
        return "ORD-" + LocalDateTime.now().toLocalDate().toString().replaceAll("-", "") + "-" +
                UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    @Override
    public OrderResponse updateOrderStatus(String orderNumber, OrderStatus status) {

        Orders order = ordersRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(status);
        order.setUpdatedAt(LocalDateTime.now());

        Orders saved = ordersRepository.save(order);
        // Send notification
        notificationService.sendOrderStatusNotification(
                order.getUserId(),
                // order.getId(),
                order.getOrderNumber(),
                status.name());
        return OrderMapper.toResponse(saved);
    }

    @Override
    public List<OrderResponse> getAllOrders() {

        return ordersRepository.findAll()
                .stream()
                .map(OrderMapper::toResponse)
                .toList();
    }

    @Override
    public void deleteOrder(String orderNumber) {

        Orders order = ordersRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        System.out.println("Deleting order");
        ordersRepository.delete(order);
    }
}
