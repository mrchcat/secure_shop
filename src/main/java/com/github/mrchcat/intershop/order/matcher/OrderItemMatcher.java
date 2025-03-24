package com.github.mrchcat.intershop.order.matcher;

import com.github.mrchcat.intershop.item.matcher.ItemMatcher;
import com.github.mrchcat.intershop.order.domain.OrderItem;
import com.github.mrchcat.intershop.order.dto.OrderItemDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class OrderItemMatcher {
    public static OrderItemDto toDto(OrderItem orderItem){
        return OrderItemDto.builder()
                .item(ItemMatcher.toDto(orderItem.getItem()))
                .count(orderItem.getQuantity())
                .unit(orderItem.getUnit())
                .sum(orderItem.getSum())
                .build();
    }

    public static List<OrderItemDto> toDto(Set<OrderItem> orderItems){
        ArrayList<OrderItemDto> list=new ArrayList<>();
        for(OrderItem oi:orderItems){
            list.add(toDto(oi));
        }
        return list;
    }
}
