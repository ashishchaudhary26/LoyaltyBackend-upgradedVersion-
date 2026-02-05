// package com.controller;

// import com.dto.AddCartItemRequest;
// import com.dto.CartDto;
// import com.dto.CartItemDto;
// import com.dto.UpdateCartItemRequest;
// import com.service.CartService;

// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.tags.Tag;

// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;
// import javax.validation.Valid;

// @Tag(name = "Cart", description = "Shopping cart APIs")
// @RestController
// @RequestMapping("/api/v1/cart")
// public class CartController {

//     private final CartService cartService;

//     public CartController(CartService cartService) { 
//         this.cartService = cartService; 
//     }

//     // ---------------- Public / Guest Cart ----------------

//     @Operation(summary = "Get cart by cart_uuid (guest users)")
//     @GetMapping
//     public ResponseEntity<CartDto> getCart(@RequestParam(name = "cart_uuid", required = false) String cartUuid,
//                                            @RequestHeader(name = "X-USER-ID", required = false) Long userId) {
//         if (userId != null) {
//             CartDto dto = cartService.getCartByUserId(userId);
//             return dto == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(dto);
//         }
//         if (cartUuid != null) {
//             CartDto dto = cartService.getCartByUuid(cartUuid);
//             return dto == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(dto);
//         }
//         return ResponseEntity.badRequest().build();
//     }

//     // ---------------- Authenticated Endpoints ----------------

//     @Operation(summary = "Add item to cart (authenticated users)")
//     @PostMapping("/items")
//     public ResponseEntity<CartItemDto> addItem(@RequestHeader("X-USER-ID") Long userId,
//                                                @Valid @RequestBody AddCartItemRequest req) {
//         req.setUserId(userId); // enforce user ID from header
//         CartItemDto created = cartService.addItem(req);
//         return ResponseEntity.status(201).body(created);
//     }

//     @Operation(summary = "Update cart item quantity (authenticated users)")
//     @PutMapping("/items/{itemId}")
//     public ResponseEntity<CartItemDto> updateItem(@RequestHeader("X-USER-ID") Long userId,
//                                                   @PathVariable Long itemId,
//                                                   @Valid @RequestBody UpdateCartItemRequest req) {
//         req.setUserId(userId);
//         CartItemDto updated = cartService.updateItem(itemId, req);
//         return ResponseEntity.ok(updated);
//     }

//     @Operation(summary = "Remove item from cart (authenticated users)")
//     @DeleteMapping("/items/{itemId}")
//     public ResponseEntity<Void> removeItem(@RequestHeader("X-USER-ID") Long userId,
//                                            @PathVariable Long itemId) {
//         cartService.removeItem(userId, itemId);
//         return ResponseEntity.noContent().build();
//     }

//     @Operation(summary = "Clear cart by userId or cart_uuid")
//     @DeleteMapping
//     public ResponseEntity<Void> clearCart(@RequestHeader(name = "X-USER-ID", required = false) Long userId,
//                                           @RequestParam(name = "cart_uuid", required = false) String cartUuid) {
//         if (userId != null) {
//             cartService.clearCartByUserId(userId);
//             return ResponseEntity.noContent().build();
//         }
//         if (cartUuid != null) {
//             cartService.clearCart(cartUuid);
//             return ResponseEntity.noContent().build();
//         }
//         return ResponseEntity.badRequest().build();
//     }
// }
package com.controller;

import com.dto.AddCartItemRequest;
import com.dto.CartDto;
import com.dto.CartItemDto;
import com.dto.UpdateCartItemRequest;
import com.service.CartService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Cart", description = "Shopping cart APIs")
@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) { 
        this.cartService = cartService; 
    }

    // ---------------- Public / Guest Cart ----------------

    @Operation(summary = "Get cart by cart_uuid (guest users) or userId")
    @GetMapping
    public ResponseEntity<CartDto> getCart(
            @RequestParam(name = "cart_uuid", required = false) String cartUuid,
            @RequestHeader(name = "X-USER-ID", required = false) String userId) {

        if (userId != null) {
            Long uid = Long.parseLong(userId);
            CartDto dto = cartService.getCartByUserId(uid);
            return dto == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(dto);
        }

        if (cartUuid != null) {
            CartDto dto = cartService.getCartByUuid(cartUuid);
            return dto == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(dto);
        }

        return ResponseEntity.badRequest().build();
    }

    // ---------------- Authenticated Endpoints ----------------

    @Operation(summary = "Add item to cart (authenticated users)")
    @PostMapping("/items")
    public ResponseEntity<CartItemDto> addItem(
            @RequestHeader("X-USER-ID") String userId,
            @Valid @RequestBody AddCartItemRequest req) {

                    System.out.println("X-USER-ID RECEIVED = " + userId);   

        Long uid = Long.parseLong(userId);
        req.setUserId(uid);  // enforce user from gateway

        CartItemDto created = cartService.addItem(req);
        return ResponseEntity.status(201).body(created);
    }

    @Operation(summary = "Update cart item quantity (authenticated users)")
    @PutMapping("/items/{itemId}")
    public ResponseEntity<CartItemDto> updateItem(
            @RequestHeader("X-USER-ID") String userId,
            @PathVariable Long itemId,
            @Valid @RequestBody UpdateCartItemRequest req) {

        Long uid = Long.parseLong(userId);
        req.setUserId(uid);

        CartItemDto updated = cartService.updateItem(itemId, req);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Remove item from cart (authenticated users)")
    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> removeItem(
            @RequestHeader("X-USER-ID") String userId,
            @PathVariable Long itemId) {

        Long uid = Long.parseLong(userId);
        cartService.removeItem(uid, itemId);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Clear cart by userId or cart_uuid")
    @DeleteMapping
    public ResponseEntity<Void> clearCart(
            @RequestHeader(name = "X-USER-ID", required = false) String userId,
            @RequestParam(name = "cart_uuid", required = false) String cartUuid) {

        if (userId != null) {
            Long uid = Long.parseLong(userId);
            cartService.clearCartByUserId(uid);
            return ResponseEntity.noContent().build();
        }

        if (cartUuid != null) {
            cartService.clearCart(cartUuid);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.badRequest().build();
    }
}
