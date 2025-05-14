package com.github.mrchcat.intershop.order.controller;

import com.github.mrchcat.intershop.order.dto.OrderDto;
import com.github.mrchcat.intershop.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @Value("${application.user_id}")
    private long userId;

    @GetMapping("/orders")
    Mono<Rendering> getOrders() {
        Mono<List<OrderDto>> orderDtos = orderService.getOrders(userId);
        return Mono.just(Rendering.view("orders")
                .modelAttribute("orders", orderDtos)
                .build());
    }

    @GetMapping("/orders/{id}")
    Mono<Rendering> getOrders(@PathVariable("id") long orderId,
                              @RequestParam(name = "newOrder", defaultValue = "false") boolean newOrder) {
        Mono<OrderDto> orderDto = orderService.getOrder(userId, orderId);
        return Mono.just(Rendering.view("order")
                .modelAttribute("order", orderDto)
                .modelAttribute("newOrder", newOrder)
                .build());
    }
}
