package com.github.mrchcat.intershop.cart.service;

import com.github.mrchcat.intershop.cart.dto.CartItemsDto;
import com.github.mrchcat.intershop.enums.CartAction;
import com.github.mrchcat.intershop.item.domain.Item;

import java.util.Map;

public interface CartService {

    Map<Item, Long> getCartItemsForUser(long userId);

    void changeCart(long userId, Item item, CartAction action);

    CartItemsDto getCartItems(long userId);

    long buyCart(long userId);

}
