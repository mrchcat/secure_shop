package com.github.mrchcat.intershop.order.controller;

import com.github.mrchcat.intershop.order.domain.Order;
import com.github.mrchcat.intershop.order.dto.OrderDto;
import com.github.mrchcat.intershop.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
//
//    @Value("${application.user_id}")
//    private long userId;
//
//    @GetMapping("/orders")
//    String getOrders(Model model) {
//        Flux<OrderDto> orderDtos = orderService.getOrders(userId);
//        model.addAttribute("orders", orderDtos);
//        return "orders";
//    }
//
//    @GetMapping("/orders/{id}")
//    String getOrders(Model model,
//                     @ModelAttribute("order") Order order,
//                     @RequestParam(name = "newOrder", defaultValue = "false") boolean newOrder) {
//        Mono<OrderDto> orderDto = orderService.getOrder(userId, order.getId());
//        model.addAttribute("order", orderDto);
//        model.addAttribute("newOrder", newOrder);
//        return "order";
//    }

}
