package com.github.mrchcat.intershop.order.service;

import com.github.mrchcat.intershop.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

//    @Override
//    @Transactional
//    public Mono<Order> makeNewOrder(User user) {
//        Order order = Order.builder()
//                .userId(user.getId())
//                .build();
//        return orderRepository.save(order);
//    }
//
//    @Override
//    public Flux<OrderDto> getOrders(long userId) {
//        Flux<Order> orders = orderRepository.findAllByUserId(userId);
//        return OrderMatcher.toDto(orders);
//    }
//
//    @Override
//    public Mono<OrderDto> getOrder(long userId, long orderId) {
//        Mono<Order> order = orderRepository
//                .findByOrderIdByUserId(userId, orderId)
//                .switchIfEmpty(Mono.error(new IllegalArgumentException("Order not found")));
//        return OrderMatcher.toDto(order);
//    }
}
