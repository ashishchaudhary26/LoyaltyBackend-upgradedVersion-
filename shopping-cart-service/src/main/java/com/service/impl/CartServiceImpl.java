package com.service.impl;

import com.dto.AddCartItemRequest;
import com.dto.CartDto;
import com.dto.CartItemDto;
import com.dto.UpdateCartItemRequest;
import com.entity.CartItems;
import com.entity.Carts;
import com.mapper.CartMapper;
import com.repository.CartItemsRepository;
import com.repository.CartsRepository;
import com.service.CartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    private final CartsRepository cartsRepository;
    private final CartItemsRepository cartItemsRepository;

    public CartServiceImpl(CartsRepository cartsRepository,
                           CartItemsRepository cartItemsRepository) {
        this.cartsRepository = cartsRepository;
        this.cartItemsRepository = cartItemsRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public CartDto getCartByUuid(String cartUuid) {
        Optional<Carts> opt = cartsRepository.findByCartUuid(cartUuid);
        return opt.map(CartMapper::toDto).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public CartDto getCartByUserId(Long userId) {
        Optional<Carts> opt = cartsRepository.findByUserId(userId);
        return opt.map(CartMapper::toDto).orElse(null);
    }

     @Override
    public CartItemDto addItem(AddCartItemRequest req) {
        // find or create cart  ,this part is wrongly implemented
          Carts cart = findOrCreateCart(req.getCartUuid(), req.getUserId());

        // If product already in cart, update quantity
        Optional<CartItems> existing = cartItemsRepository.findByCartIdAndProductId(cart.getId(), req.getProductId());
        if (existing.isPresent()) {
            CartItems item = existing.get();
            item.setQuantity(item.getQuantity() + req.getQuantity());
            // if unitPrice provided, update unit price (or keep existing)
            if (req.getUnitPrice() != null) item.setUnitPrice(req.getUnitPrice());
            item = cartItemsRepository.save(item);
            return CartMapper.toDto(item);
        }

        // create new item
        CartItems item = new CartItems();
        item.setCart(cart);
        item.setProductId(req.getProductId());
        item.setQuantity(req.getQuantity());
        item.setUnitPrice(req.getUnitPrice() != null ? req.getUnitPrice() : BigDecimal.ZERO);
        item = cartItemsRepository.save(item);

        return CartMapper.toDto(item);
    }

    @Override
    public CartDto createCartIfNotExists(String cartUuid, Long userId) {
        if (cartUuid != null) {
            Optional<Carts> exists = cartsRepository.findByCartUuid(cartUuid);
            if (exists.isPresent()) return CartMapper.toDto(exists.get());
        }
        if (userId != null) {
            Optional<Carts> exists = cartsRepository.findByUserId(userId);
            if (exists.isPresent()) return CartMapper.toDto(exists.get());
        }

        // ensure non-null cartUuid
        String finalUuid = (cartUuid != null) ? cartUuid : UUID.randomUUID().toString();
        Carts cart = new Carts(finalUuid, userId);
        cart = cartsRepository.save(cart);
        return CartMapper.toDto(cart);
    }
    

   

    @Override
    public CartItemDto updateItem(Long itemId, UpdateCartItemRequest req) {
        CartItems item = cartItemsRepository.findById(itemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart item not found"));
        item.setQuantity(req.getQuantity());
        item = cartItemsRepository.save(item);
        return CartMapper.toDto(item);
    }

    @Override
    public void removeItem(Long itemId) {
        if (!cartItemsRepository.existsById(itemId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart item not found");
        }
        cartItemsRepository.deleteById(itemId);
    }

    @Override
    public void clearCart(String cartUuid) {
        Carts cart = cartsRepository.findByCartUuid(cartUuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found"));
        cartItemsRepository.deleteByCartId(cart.getId());
    }

    // helper
    private Carts findOrCreateCart(String cartUuid, Long userId) {
        if (cartUuid != null) {
            Optional<Carts> c = cartsRepository.findByCartUuid(cartUuid);
            if (c.isPresent()) return c.get();
        }
        if (userId != null) {
            Optional<Carts> c = cartsRepository.findByUserId(userId);
            if (c.isPresent()) return c.get();
        }

        // ✅ generate UUID if missing (for logged-in users)
        String finalUuid = (cartUuid != null) ? cartUuid : UUID.randomUUID().toString();
        Carts cart = new Carts(finalUuid, userId);
        return cartsRepository.save(cart);
    }

    @Override
    public void removeItem(Long userId, Long itemId) {
        CartItems item = cartItemsRepository.findById(itemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart item not found"));
        if (!item.getCart().getUserId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot remove item from another user's cart");
        }
        cartItemsRepository.delete(item);
    }

    @Override
    public void clearCartByUserId(Long userId) {
        Carts cart = cartsRepository.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found for user"));
        cartItemsRepository.deleteByCartId(cart.getId());
    }

}
