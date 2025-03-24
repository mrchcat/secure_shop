package com.github.mrchcat.intershop.order.repository;

import com.github.mrchcat.intershop.order.domain.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("""
            SELECT o
            FROM Order AS o
            WHERE o.user.id=:userId
            """)
    @EntityGraph("graph.order.items")
    List<Order> findAllByUserId(long userId);

    @Query("""
            SELECT o
            FROM Order AS o
            WHERE o.user.id=:userId AND o.id=:orderId
            """)
    @EntityGraph("graph.order.items")
    Optional<Order> findByOrderIdByUserId(long userId, long orderId);
}
