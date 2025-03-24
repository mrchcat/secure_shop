package com.github.mrchcat.intershop.cart.matcher;

import com.github.mrchcat.intershop.cart.domain.CartItem;
import com.github.mrchcat.intershop.item.dto.ItemDto;
import com.github.mrchcat.intershop.item.matcher.ItemMatcher;

import java.util.List;
import java.util.Set;

public class CartItemMatcher {
    public static List<ItemDto> toDto(Set<CartItem> cartItemSets) {
        return cartItemSets.stream()
                .map(ci -> ItemMatcher.toDto(ci.getItem(), true, ci.getQuantity()))
                .toList();
    }
}
