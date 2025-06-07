package com.github.mrchcat.intershop.cart.service;

import com.github.mrchcat.intershop.cart.domain.Cart;
import reactor.core.publisher.Mono;

public interface AddCartService {

    Mono<Cart> createCartForUser(long userId);
}
