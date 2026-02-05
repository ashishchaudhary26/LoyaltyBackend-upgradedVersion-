// com/service/ShippingAddressService.java
package com.service;

import com.dto.CreateShippingAddressRequest;
import com.dto.ShippingAddressDto;

import java.util.List;

public interface ShippingAddressService {

    List<ShippingAddressDto> listByUserId(Long userId);

    ShippingAddressDto create(CreateShippingAddressRequest req);

    void deleteForUser(Long userId, Long addressId);   // 🔹 NEW
}
