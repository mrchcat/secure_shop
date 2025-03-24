package com.github.mrchcat.intershop.cart.service;

import com.github.mrchcat.intershop.cart.domain.Cart;
import com.github.mrchcat.intershop.cart.domain.CartItem;
import com.github.mrchcat.intershop.cart.dto.CartItemsDto;
import com.github.mrchcat.intershop.cart.repository.CartRepository;
import com.github.mrchcat.intershop.enums.CartAction;
import com.github.mrchcat.intershop.item.domain.Item;
import com.github.mrchcat.intershop.item.dto.ItemDto;
import com.github.mrchcat.intershop.matcher.CartItemMatcher;
import com.sun.jdi.InternalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;

    @Override
    public Map<Item, Long> getCartItemsForUser(long userId) {
        List<Cart> carts = cartRepository.findAllByUserId(userId);
        checkCarts(carts, userId);
        return carts.getFirst()
                .getCartItems()
                .stream()
                .collect(Collectors.toMap(CartItem::getItem, CartItem::getQuantity));
    }


    @Override
    @Transactional
    public void changeCart(long userId, Item item, CartAction action) {
        List<Cart> carts = cartRepository.findAllByUserId(userId);
        checkCarts(carts, userId);
        Cart cart = carts.getFirst();
        CartItem newCartItem = CartItem.builder()
                .cart(cart)
                .item(item)
                .quantity(1)
                .build();
        Set<CartItem> cartItems = cart.getCartItems();

        switch (action) {
            case plus -> {
                if (cartItems.contains(newCartItem)) {
                    CartItem existingCartItem = getCartItem(cartItems, newCartItem);
                    long oldQuantity = existingCartItem.getQuantity();
                    existingCartItem.setQuantity(oldQuantity + 1);
                    cartItems.remove(existingCartItem);
                    cartItems.add(existingCartItem);
                } else {
                    cartItems.add(newCartItem);
                }
            }
            case minus -> {
                CartItem existingCartItem = getCartItem(cartItems, newCartItem);
                long oldQuantity = existingCartItem.getQuantity();
                if (oldQuantity > 0) {
                    cartItems.remove(existingCartItem);
                    existingCartItem.setQuantity(oldQuantity - 1);
                    cartItems.add(existingCartItem);
                } else {
                    return;
                }
            }
            case delete -> cartItems.remove(newCartItem);
        }
        cartRepository.save(cart);
    }

    @Override
    public CartItemsDto getCartItems(long userId) {
        List<Cart> carts = cartRepository.findAllByUserId(userId);
        checkCarts(carts, userId);
        Set<CartItem> cartItems = carts.getFirst().getCartItems();
        if (cartItems.isEmpty()) {
            return CartItemsDto.builder()
                    .itemDtoList(Collections.emptyList())
                    .isCartEmpty(true)
                    .total(BigDecimal.ZERO)
                    .build();
        }
        BigDecimal total = cartItems.stream()
                .map(ci -> ci.getItem().getPrice().multiply(BigDecimal.valueOf(ci.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return CartItemsDto.builder()
                .itemDtoList(CartItemMatcher.toDto(cartItems))
                .isCartEmpty(false)
                .total(total)
                .build();
    }

    private CartItem getCartItem(Set<CartItem> cartItems, CartItem cartItem) {
        return cartItems.stream()
                .filter(ci -> ci.equals(cartItem))
                .findFirst()
                .orElseThrow(InternalException::new);
    }

    private void checkCarts(List<Cart> carts, long userId) {
        if (carts.isEmpty()) {
            throw new InternalException("корзина отсутствует для пользователя " + userId);
        }
        if (carts.size() > 1) {
            throw new UnsupportedOperationException("множественные корзины для пользователя " + userId);
        }
    }

}
