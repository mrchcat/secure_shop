package com.github.mrchcat.intershop.order.controller;

import com.github.mrchcat.intershop.order.dto.OrderDto;
import com.github.mrchcat.intershop.order.service.OrderService;
import com.github.mrchcat.intershop.secure.domain.Credentials;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;


    @GetMapping("/orders")
    Mono<Rendering> getOrders(Authentication authentication) {
        Mono<List<OrderDto>> orderDtos = orderService.getOrders(getUserId(authentication));
        var isAuth = Mono.just(authentication.isAuthenticated());
        return Mono.just(Rendering
                .view("orders")
                .modelAttribute("orders", orderDtos)
                .modelAttribute("isAuth", isAuth)
                .build());
    }

    @GetMapping("/orders/{id}")
    Mono<Rendering> getOrders(@PathVariable("id") long orderId,
                              @RequestParam(name = "newOrder", defaultValue = "false") boolean newOrder,
                              Authentication authentication) {
        Mono<OrderDto> orderDto = orderService.getOrder(getUserId(authentication), orderId);
        var isAuth = Mono.just(authentication.isAuthenticated());
        return Mono.just(Rendering
                .view("order")
                .modelAttribute("order", orderDto)
                .modelAttribute("newOrder", newOrder)
                .modelAttribute("isAuth", isAuth)
                .build());
    }

    private long getUserId(Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof Credentials credentials) {
            return credentials.getUserId();
        }
        throw new NoSuchElementException("userId не найден");
    }
}
