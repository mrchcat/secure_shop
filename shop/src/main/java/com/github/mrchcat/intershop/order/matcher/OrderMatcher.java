package com.github.mrchcat.intershop.order.matcher;

import com.github.mrchcat.intershop.item.domain.Item;
import com.github.mrchcat.intershop.order.domain.Order;
import com.github.mrchcat.intershop.order.domain.OrderItem;
import com.github.mrchcat.intershop.order.dto.OrderDto;
import com.github.mrchcat.intershop.order.dto.OrderItemDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class OrderMatcher {

    public static Mono<OrderDto> toDto(Mono<Order> order, Flux<OrderItem> orderItems, Flux<Item> items) {
        return Mono.zip(order,
                        orderItems.collectList(),
                        items.collectMap(Item::getId, Function.identity()))
                .map(tuple -> {
                    Order o = tuple.getT1();
                    return OrderMatcher.makeOrderDto(
                            o,
                            tuple.getT2(),
                            tuple.getT3());
                });
    }

    public static Flux<OrderDto> toDto(Flux<Order> order, Flux<OrderItem> orderItems, Flux<Item> items) {
        return Flux.zip(order,
                        orderItems.collectMultimap(OrderItem::getOrderId, Function.identity()).repeat(),
                        items.collectMap(Item::getId, Function.identity()).repeat())
                .map(tuple -> {
                    Order o = tuple.getT1();
                    return OrderMatcher.makeOrderDto(
                            o,
                            tuple.getT2().get(o.getId()),
                            tuple.getT3());
                });
    }

    private static OrderDto makeOrderDto(Order o, Collection<OrderItem> orderItemList, Map<Long, Item> itemMap) {
        List<OrderItemDto> orderItemDtoList = orderItemList.stream()
                .map(oi -> OrderItemMatcher.toDto(oi, itemMap.get(oi.getItemId())))
                .toList();
        return OrderDto.builder()
                .id(o.getId())
                .orderItems(orderItemDtoList)
                .totalSum(o.getTotalSum())
                .build();
    }
}
