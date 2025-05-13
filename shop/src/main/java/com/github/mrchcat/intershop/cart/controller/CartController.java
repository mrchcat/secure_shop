package com.github.mrchcat.intershop.cart.controller;

import com.github.mrchcat.intershop.cart.dto.CartItemsDto;
import com.github.mrchcat.intershop.cart.service.CartService;
import com.github.mrchcat.intershop.item.dto.ActionDto;
import com.github.mrchcat.intershop.item.service.ItemService;
import com.github.mrchcat.intershop.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final ItemService itemService;
    private final OrderService orderService;

    @Value("${application.user_id}")
    private long userId;

    @GetMapping("/cart/items")
    public Mono<Rendering> getCartItems() {
        Mono<CartItemsDto> cartItemsDto = cartService.getCartItems(userId);
        return Mono.just(Rendering
                .view("cart")
                .modelAttribute("items", cartItemsDto.map(CartItemsDto::getItemDtoList))
                .modelAttribute("total", cartItemsDto.map(CartItemsDto::getTotal))
                .modelAttribute("empty", cartItemsDto.map(CartItemsDto::isCartEmpty))
                .modelAttribute("enablePayment", cartItemsDto.map(CartItemsDto::isEnablePayment))
                .modelAttribute("payServiceError",cartItemsDto.map(CartItemsDto::getPayError))
                .build());
    }

    @PostMapping("/cart/items/{id}")
    public Mono<Rendering> updateCartInMain(@PathVariable("id") long itemId,
                                            @ModelAttribute("actionDto") ActionDto actionDto) {
        return itemService
                .changeCart(userId, itemId, actionDto.getAction())
                .thenReturn(Rendering.view("redirect:/cart/items").build());
    }

    @PostMapping("/buy")
    public Mono<Rendering> buyCart() {
        orderService.buyCart(userId).subscribe(r-> System.out.println("Результат "+r.getUserId()));
        return orderService
                .buyCart(userId)
                .map(order -> Rendering.view("redirect:/orders/" + order.getId() + "?newOrder=true").build());
    }

}
