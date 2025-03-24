package com.github.mrchcat.intershop.order.matcher;

import com.github.mrchcat.intershop.order.domain.Order;
import com.github.mrchcat.intershop.order.dto.OrderDto;

import java.util.List;

public class OrderMatcher {

    public static List<OrderDto> toDto(List<Order> orders) {
        return orders.stream()
                .map(OrderMatcher::toDto)
                .toList();
    }

    public static OrderDto toDto(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .orderItems(OrderItemMatcher.toDto(order.getOrderItems()))
                .totalSum(order.getTotalSum())
                .build();
    }
}
