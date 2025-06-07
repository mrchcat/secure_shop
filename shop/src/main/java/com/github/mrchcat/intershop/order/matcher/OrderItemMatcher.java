package com.github.mrchcat.intershop.order.matcher;

import com.github.mrchcat.intershop.item.domain.Item;
import com.github.mrchcat.intershop.item.matcher.ItemMatcher;
import com.github.mrchcat.intershop.order.domain.OrderItem;
import com.github.mrchcat.intershop.order.dto.OrderItemDto;

public class OrderItemMatcher {
    public static OrderItemDto toDto(OrderItem orderItem, Item item) {
        return OrderItemDto.builder()
                .item(ItemMatcher.toDtoWoCounts(item))
                .count(orderItem.getQuantity())
                .unit(orderItem.getUnit())
                .sum(orderItem.getSum())
                .build();
    }
}
