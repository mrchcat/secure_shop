package com.github.mrchcat.intershop.order.repository;


import com.github.mrchcat.intershop.order.domain.Order;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderRepository extends ReactiveCrudRepository<Order, Long> {

    @Query("""
            SELECT id,user_id,created,total_sum
            FROM orders
            WHERE user_id=:userId
            ORDER BY created DESC
            """)
    Flux<Order> findAllByUserId(long userId);

    @Query("""
            SELECT id,user_id,created,total_sum
            FROM orders
            WHERE id=:orderId AND user_id=:userId
            """)
    Mono<Order> findByOrderIdByUserId(long userId, long orderId);
}
