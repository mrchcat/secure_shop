package com.github.mrchcat.intershop.cart.repository;

import com.github.mrchcat.intershop.cart.domain.Cart;
import com.github.mrchcat.intershop.cart.domain.CartItem;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface CartItemRepository extends ReactiveCrudRepository<Cart, Long> {

    @Query("""
            SELECT ci.id,ci.cart_id, ci.item_id,ci.quantity
            FROM users AS u
            JOIN carts AS crt ON u.id=crt.user_id
            JOIN cart_item AS ci ON crt.id=ci.cart_id
            WHERE u.id=:userId
            """)
    Flux<CartItem> findByUserId(long userId);
}
