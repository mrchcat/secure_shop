package com.github.mrchcat.intershop.cart.service;

import com.github.mrchcat.intershop.item.domain.Item;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface CartService {

    Mono<Map<Long, Long>> getCartItemsForUser(long userId);

//    Mono<Void> changeCart(long userId, Item item, CartAction action);
//
//    Mono<CartItemsDto> getCartItems(long userId);
//
//    Mono<Long> buyCart(long userId);

}
