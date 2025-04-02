package com.github.mrchcat.intershop.order.repository;


import com.github.mrchcat.intershop.order.domain.Order;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderRepository extends ReactiveCrudRepository<Order, Long> {

    @Query("""
            SELECT o
            FROM Order AS o
            WHERE o.user.id=:userId
            ORDER BY o.created DESC
            """)
    Flux<Order> findAllByUserId(long userId);

    @Query("""
            SELECT o
            FROM Order AS o
            WHERE o.user.id=:userId AND o.id=:orderId
            """)
    Mono<Order> findByOrderIdByUserId(long userId, long orderId);
}
