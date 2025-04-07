package com.github.mrchcat.intershop.cart.matcher;

import com.github.mrchcat.intershop.cart.domain.CartItemPrice;
import com.github.mrchcat.intershop.item.dto.ItemDto;

import java.util.List;

public class CartItemPriceMatcher {

    public static List<ItemDto> toDto(List<CartItemPrice> list) {
        return list.stream()
                .map(CartItemPriceMatcher::toDto)
                .toList();
    }

    public static ItemDto toDto(CartItemPrice cip) {
        return ItemDto.builder()
                .id(cip.getItemId())
                .title(cip.getTitle())
                .description(cip.getDescription())
                .price(cip.getPrice())
                .imgPath(cip.getImgPath())
                .inCart(true)
                .count(cip.getQuantity())
                .unit(cip.getUnit())
                .build();
    }


}
