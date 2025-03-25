package com.github.mrchcat.intershop.order.service;

import com.github.mrchcat.intershop.order.domain.Order;
import com.github.mrchcat.intershop.order.dto.OrderDto;
import com.github.mrchcat.intershop.order.matcher.OrderMatcher;
import com.github.mrchcat.intershop.order.repository.OrderRepository;
import com.github.mrchcat.intershop.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public Order makeNewOrder(User user) {
        Order order = Order.builder()
                .user(user)
                .build();
        return orderRepository.save(order);
    }

    @Override
    public List<OrderDto> getOrders(long userId) {
        List<Order> orders = orderRepository.findAllByUserId(userId);
        return OrderMatcher.toDto(orders);
    }

    @Override
    public OrderDto getOrder(long userId, long orderId) {
        Order order = orderRepository
                .findByOrderIdByUserId(userId, orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        return OrderMatcher.toDto(order);
    }
}
