package com.github.mrchcat.intershop.cart.repository;

import com.github.mrchcat.intershop.cart.domain.CartItemPrice;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface CartItemPriceRepository extends R2dbcRepository<CartItemPrice, Long> {

    @Query("""
            SELECT ci.id,ci.cart_id, it.price, it.name, it.description, it.picture_path, it.unit,ci.item_id,ci.quantity
            FROM cart_item AS ci
            JOIN items AS it ON it.id=ci.item_id
            WHERE cart_id=:cartId
            ORDER BY ci.id ASC
            """)
    Flux<CartItemPrice> findByCart(Mono<Long> cartId);
}

