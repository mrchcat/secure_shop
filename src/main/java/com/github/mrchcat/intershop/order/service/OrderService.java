package com.github.mrchcat.intershop.order.service;

import com.github.mrchcat.intershop.order.domain.Order;
import com.github.mrchcat.intershop.order.domain.OrderItem;
import com.github.mrchcat.intershop.order.dto.OrderDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface OrderService {
    Mono<Order> makeNewOrder(long userId);

    Mono<Order> saveOrder(Order order);

    Mono<List<OrderDto>> getOrders(long userId);

    Mono<OrderDto> getOrder(long userId, long orderId);

    Flux<OrderItem> saveAllOrderItems(List<OrderItem> orderItems);

}
