package com.github.mrchcat.intershop.order.controller;

import com.github.mrchcat.intershop.order.dto.OrderDto;
import com.github.mrchcat.intershop.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @Value("${application.user_id}")
    private long userId;

    @GetMapping("/orders")
    String getOrders(Model model) {
        List<OrderDto> orderDtos = orderService.getOrders(userId);
        model.addAttribute("orders", orderDtos);
        return "orders";
    }

    @GetMapping("/orders/{id}")
    String getOrders(Model model,
                     @PathVariable("id") long id,
                     @RequestParam(name = "newOrder", defaultValue = "false") boolean newOrder) {
        OrderDto orderDto = orderService.getOrder(userId, id);
        model.addAttribute("order", orderDto);
        model.addAttribute("newOrder", newOrder);
        return "order";
    }

}
