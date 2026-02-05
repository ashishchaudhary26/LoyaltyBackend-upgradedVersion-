package com.mapper;

import com.dto.ShippingAddressDto;
import com.entity.ShippingAddresses;

public class ShippingAddressMapper {

    public static ShippingAddressDto toDto(ShippingAddresses e) {
        if (e == null) return null;

        ShippingAddressDto dto = new ShippingAddressDto();
        dto.setId(e.getId());
        dto.setUserId(e.getUserId());
        dto.setFullName(e.getFullName());
        dto.setMobileNumber(e.getMobileNumber());
        dto.setAddressLine(e.getAddressLine());
        dto.setCity(e.getCity());
        dto.setState(e.getState());
        dto.setPincode(e.getPincode());
        dto.setCountry(e.getCountry());
        dto.setCreatedAt(e.getCreatedAt());
        return dto;
    }

    public static void updateEntityFromDto(ShippingAddressDto dto, ShippingAddresses e) {
        e.setFullName(dto.getFullName());
        e.setMobileNumber(dto.getMobileNumber());
        e.setAddressLine(dto.getAddressLine());
        e.setCity(dto.getCity());
        e.setState(dto.getState());
        e.setPincode(dto.getPincode());
        e.setCountry(dto.getCountry());
        // userId + createdAt handled separately
    }
}
