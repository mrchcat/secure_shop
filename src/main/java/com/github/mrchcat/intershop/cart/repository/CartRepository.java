package com.github.mrchcat.intershop.cart.repository;

import com.github.mrchcat.intershop.cart.domain.Cart;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("""
            SELECT c
            FROM Cart AS c
            WHERE c.user.id=:userId
            """)
    @EntityGraph("graph.cart.items")
    Optional<Cart> findByUserId(long userId);

}
