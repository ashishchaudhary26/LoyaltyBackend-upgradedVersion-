// package com.service.impl;

// import com.dto.CreateShippingAddressRequest;
// import com.dto.ShippingAddressDto;
// import com.entity.ShippingAddresses;
// import com.repository.ShippingAddressesRepository;
// import com.service.ShippingAddressService;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import java.time.LocalDateTime;
// import java.util.List;
// import java.util.stream.Collectors;

// @Service
// @Transactional
// public class ShippingAddressServiceImpl implements ShippingAddressService {

//     private final ShippingAddressesRepository repo;

//     public ShippingAddressServiceImpl(ShippingAddressesRepository repo) {
//         this.repo = repo;
//     }

//     @Override
//     @Transactional(readOnly = true)
//     public List<ShippingAddressDto> listByUserId(Long userId) {
//         return repo.findByUserId(userId).stream()
//                 .map(this::toDto)
//                 .collect(Collectors.toList());
//     }
//     @Override
//     public void deleteForUser(Long userId, Long addressId) {
//         ShippingAddresses addr = repo.findById(addressId)
//                 .orElseThrow(() -> new RuntimeException("Address not found"));

//         // ensure user owns this address
//         if (!addr.getUserId().equals(userId)) {
//             throw new RuntimeException("Cannot delete another user's address");
//         }

//         repo.delete(addr);
//     }

//     @Override
//     public ShippingAddressDto create(CreateShippingAddressRequest req) {
//         ShippingAddresses e = new ShippingAddresses();
//         e.setUserId(req.getUserId());
//         e.setFullName(req.getFullName());
//         e.setMobileNumber(req.getMobileNumber());
//         e.setAddressLine(req.getAddressLine());
//         e.setCity(req.getCity());
//         e.setState(req.getState());
//         e.setPincode(req.getPincode());
//         e.setCountry(req.getCountry());
//         e.setCreatedAt(LocalDateTime.now());

//         e = repo.save(e);
//         return toDto(e);
//     }

//     private ShippingAddressDto toDto(ShippingAddresses e) {
//         ShippingAddressDto dto = new ShippingAddressDto();
//         dto.setId(e.getId());
//         dto.setUserId(e.getUserId());
//         dto.setFullName(e.getFullName());
//         dto.setMobileNumber(e.getMobileNumber());
//         dto.setAddressLine(e.getAddressLine());
//         dto.setCity(e.getCity());
//         dto.setState(e.getState());
//         dto.setPincode(e.getPincode());
//         dto.setCountry(e.getCountry());
//         return dto;
//     }
// }
