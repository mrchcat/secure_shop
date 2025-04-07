package com.github.mrchcat.intershop.order.service;

import com.github.mrchcat.intershop.item.domain.Item;
import com.github.mrchcat.intershop.item.repository.ItemRepository;
import com.github.mrchcat.intershop.order.domain.Order;
import com.github.mrchcat.intershop.order.domain.OrderItem;
import com.github.mrchcat.intershop.order.dto.OrderDto;
import com.github.mrchcat.intershop.order.matcher.OrderMatcher;
import com.github.mrchcat.intershop.order.repository.OrderItemRepository;
import com.github.mrchcat.intershop.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ItemRepository itemRepository; //TODO не вызывать репозитории напрямую, только через сервисы

    @Override
    @Transactional
    public Mono<Order> makeNewOrder(long userId) {
        Order order = Order.builder()
                .userId(userId)
                .created(LocalDateTime.now())
                .build();
        return orderRepository
                .save(order)
                .doOnNext(o -> o.setNumber(generateOrderNumber(o)))
                .flatMap(orderRepository::save);
    }

    private String generateOrderNumber(Order order) {
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedString = date.format(formatter);
        return formattedString + "-" + order.getId() + "-" + (int) (Math.random() * 1000);
    }


    @Override
    public Mono<Order> saveOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Mono<List<OrderDto>> getOrders(long userId) {
        Flux<Order> orders = orderRepository.findAllByUserId(userId);
        orders.subscribe(System.out::println);
        Flux<OrderItem> orderItems = orderItemRepository.findAllByOrders(orders.map(Order::getId));
        Flux<Item> items = itemRepository.findAllForOrders(orders.map(Order::getId));
        return OrderMatcher.toDto(orders, orderItems, items)
                .collectList();
    }

    @Override
    public Mono<OrderDto> getOrder(long userId, long orderId) {
        Mono<Order> order = orderRepository
                .findByOrderIdByUserId(userId, orderId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Order not found")))
                .cache();
        Flux<OrderItem> orderItems = orderItemRepository.findAllByOrder(order.map(Order::getId));
        Flux<Item> items = itemRepository.findAllForOrder(order.map(Order::getId));

        return OrderMatcher.toDto(order, orderItems, items);
    }

    @Override
    public Flux<OrderItem> saveAllOrderItems(List<OrderItem> orderItems) {
        return orderItemRepository.saveAll(orderItems);
    }
}
