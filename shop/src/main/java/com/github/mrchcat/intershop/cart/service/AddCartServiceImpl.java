package com.github.mrchcat.intershop.cart.service;

import com.github.mrchcat.intershop.cart.domain.Cart;
import com.github.mrchcat.intershop.cart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class AddCartServiceImpl implements AddCartService {
    private final CartRepository cartRepository;

    @Override
    public Mono<Cart> createCartForUser(long userId) {
        return cartRepository.save(new Cart(userId));
    }
}
