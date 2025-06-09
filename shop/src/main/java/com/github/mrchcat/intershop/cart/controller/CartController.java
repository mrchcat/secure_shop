package com.github.mrchcat.intershop.cart.controller;

import com.github.mrchcat.intershop.cart.dto.CartItemsDto;
import com.github.mrchcat.intershop.cart.service.CartService;
import com.github.mrchcat.intershop.item.dto.ActionDto;
import com.github.mrchcat.intershop.item.service.ItemService;
import com.github.mrchcat.intershop.order.service.OrderService;
import com.github.mrchcat.intershop.secure.domain.Credentials;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final ItemService itemService;
    private final OrderService orderService;

    @GetMapping("/cart/items")
    public Mono<Rendering> getCartItems(Authentication authentication) {
        Mono<CartItemsDto> cartItemsDto = cartService.getCartItems(getUserId(authentication));
        var isAuth = Mono.just(authentication.isAuthenticated());
        return Mono.just(Rendering
                .view("cart")
                .modelAttribute("items", cartItemsDto.map(CartItemsDto::getItemDtoList))
                .modelAttribute("total", cartItemsDto.map(CartItemsDto::getTotal))
                .modelAttribute("empty", cartItemsDto.map(CartItemsDto::isCartEmpty))
                .modelAttribute("enablePayment", cartItemsDto.map(CartItemsDto::isEnablePayment))
                .modelAttribute("payServiceError", cartItemsDto.map(CartItemsDto::getPayError))
                .modelAttribute("isAuth", isAuth)
                .build());
    }

    @PostMapping("/cart/items/{id}")
    public Mono<Rendering> updateCartInMain(@PathVariable("id") long itemId,
                                            @ModelAttribute("actionDto") ActionDto actionDto,
                                            Authentication authentication) {
        return itemService
                .changeCart(getUserId(authentication), itemId, actionDto.getAction())
                .thenReturn(Rendering.view("redirect:/cart/items").build());
    }

    @PostMapping("/buy")
    public Mono<Rendering> buyCart(Authentication authentication) {
        return orderService
                .buyCart(getUserId(authentication))
                .map(order -> Rendering.view("redirect:/orders/" + order.getId() + "?newOrder=true").build());
    }

    private long getUserId(Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof Credentials credentials) {
            return credentials.getUserId();
        }
        throw new NoSuchElementException("userId не найден");
    }

}
