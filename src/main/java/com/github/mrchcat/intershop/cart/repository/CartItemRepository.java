package com.github.mrchcat.intershop.cart.repository;

import com.github.mrchcat.intershop.cart.domain.CartItem;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CartItemRepository extends ReactiveCrudRepository<CartItem, Long> {

    @Query("""
            SELECT id,cart_id, item_id,quantity
            FROM cart_item
            WHERE cart_id=:cartId
            """)
    Flux<CartItem> findByCart(Mono<Long> cartId);

    @Query("""
            SELECT id,cart_id, item_id,quantity
            FROM cart_item
            WHERE cart_id=:cartId AND item_id=:itemId
            """)
    Mono<CartItem> findByCartAndItem(Mono<Long> cartId, Mono<Long> itemId);

    @Query("""
            DELETE FROM cart_item
            WHERE cart_id=:cartId AND item_id=:itemId
            """)
    Mono<Void> deleteByCartAndItem(Mono<Long> cartId, Mono<Long> itemId);


    @Query("""
            DELETE FROM cart_item
            WHERE cart_id=:cartId
            """)
    Mono<Void> clearCart(Long cartId);

}
