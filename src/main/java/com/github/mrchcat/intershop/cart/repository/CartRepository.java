package com.github.mrchcat.intershop.cart.repository;


import com.github.mrchcat.intershop.cart.domain.Cart;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface CartRepository extends ReactiveCrudRepository<Cart, Long> {

    @Query("""
            SELECT id, user_id
            FROM carts
            WHERE user_id=:userId
            """)
    Mono<Cart> findByUserId(long userId);

}
