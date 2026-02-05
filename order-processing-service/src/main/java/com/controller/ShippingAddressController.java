package com.controller;

import com.dto.CreateShippingAddressRequest;
import com.dto.ShippingAddressDto;
import com.service.ShippingAddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Shipping Addresses", description = "Manage user shipping addresses")
@RestController
@RequestMapping("/api/v1/shipping-addresses")
public class ShippingAddressController {

    private final ShippingAddressService shippingAddressService;

    public ShippingAddressController(ShippingAddressService shippingAddressService) {
        this.shippingAddressService = shippingAddressService;
    }

    @Operation(summary = "List current user's shipping addresses")
    @GetMapping
    public ResponseEntity<List<ShippingAddressDto>> listMyAddresses(
            @RequestHeader("X-USER-ID") Long userId) {

        List<ShippingAddressDto> list = shippingAddressService.listByUserId(userId);
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Create a new shipping address for current user")
    @PostMapping
    public ResponseEntity<ShippingAddressDto> createAddress(
            @RequestHeader("X-USER-ID") Long userId,
            @Valid @RequestBody CreateShippingAddressRequest req) {

        req.setUserId(userId); // enforce from header
        ShippingAddressDto saved = shippingAddressService.create(req);
        return ResponseEntity.status(201).body(saved);
    }
    @Operation(summary = "Delete a shipping address of current user")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable Long id
    ) {
        shippingAddressService.deleteForUser(userId, id);
        return ResponseEntity.noContent().build();
    }
}
