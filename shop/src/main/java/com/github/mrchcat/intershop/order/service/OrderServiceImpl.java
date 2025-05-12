package com.github.mrchcat.intershop.order.service;

import com.github.mrchcat.client.PaymentClient;
import com.github.mrchcat.dto.Payment;
import com.github.mrchcat.intershop.cart.domain.Cart;
import com.github.mrchcat.intershop.cart.service.CartService;
import com.github.mrchcat.intershop.item.domain.Item;
import com.github.mrchcat.intershop.item.service.ItemService;
import com.github.mrchcat.intershop.order.domain.Order;
import com.github.mrchcat.intershop.order.domain.OrderItem;
import com.github.mrchcat.intershop.order.dto.OrderDto;
import com.github.mrchcat.intershop.order.matcher.OrderMatcher;
import com.github.mrchcat.intershop.order.repository.OrderItemRepository;
import com.github.mrchcat.intershop.order.repository.OrderRepository;
import com.github.mrchcat.intershop.user.domain.User;
import com.github.mrchcat.intershop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ItemService itemService;
    private final CartService cartService;
    private final UserService userService;
    private final PaymentClient paymentClient;
    @Value("${application.shop.payment_id}")
    private UUID shopPaymentId;

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
        Flux<Item> items = itemService.getItemsForOrders(orders.map(Order::getId));
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
        Flux<Item> items = itemService.getItemsForOrder(order.map(Order::getId));

        return OrderMatcher.toDto(order, orderItems, items);
    }

    @Override
    public Flux<OrderItem> saveAllOrderItems(List<OrderItem> orderItems) {
        return orderItemRepository.saveAll(orderItems);
    }

    @Override
    @Transactional
    public Mono<Order> buyCart(long userId) {
        Mono<Cart> cart = cartService.getCartForUser(userId).cache();
        Mono<Order> order = makeNewOrder(userId);
        Mono<UUID> userPaymentId = userService.getUser(userId).map(User::getPaymentId);

        return cartService.getOrderItemsForCart(cart)
                .collectList()
                .zipWith(order)
                .doOnNext(tuple -> {
                    tuple.getT1().forEach(oi -> oi.setOrderId(tuple.getT2().getId()));
                    tuple.getT1().forEach(System.out::println);
                })
                .doOnNext(tuple -> {
                            BigDecimal totalSum = tuple.getT1()
                                    .stream()
                                    .map(OrderItem::getSum)
                                    .reduce(BigDecimal.ZERO, BigDecimal::add);
                            tuple.getT2().setTotalSum(totalSum);
                        }
                )
                .flatMap(tuple -> {
                            Mono<Payment> payment = userPaymentId
                                    .map(payerId -> Payment.builder()
                                            .paymentId(UUID.randomUUID())
                                            .payer(payerId)
                                            .recipient(shopPaymentId)
                                            .amount(tuple.getT2().getTotalSum())
                                            .build())
                                    .flatMap(paymentClient::createPayment);
                            return payment.thenMany(saveAllOrderItems(tuple.getT1()))
                                    .then(cartService.clearCart(cart))
                                    .then(saveOrder(tuple.getT2()));
                        }
                );
    }
}
