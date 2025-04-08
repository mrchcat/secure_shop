package com.github.mrchcat.intershop.cart.service;

import com.github.mrchcat.intershop.cart.domain.Cart;
import com.github.mrchcat.intershop.cart.dto.CartItemsDto;
import com.github.mrchcat.intershop.enums.CartAction;
import com.github.mrchcat.intershop.item.domain.Item;
import com.github.mrchcat.intershop.order.domain.OrderItem;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface CartService {

    Mono<Map<Long, Long>> getCartItemsForUser(long userId);

    Flux<OrderItem> getOrderItemsForCart(Mono<Cart> cart);

    Mono<Void> changeCart(long userId, Mono<Item> item, CartAction action);

    Mono<CartItemsDto> getCartItems(long userId);

    Mono<Cart> getCartForUser(long userId);

    Mono<Void> clearCart(Mono<Cart> cart);
}
