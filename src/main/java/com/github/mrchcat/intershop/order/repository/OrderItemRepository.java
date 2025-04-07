package com.github.mrchcat.intershop.order.repository;

import com.github.mrchcat.intershop.order.domain.OrderItem;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderItemRepository extends ReactiveCrudRepository<OrderItem, Long> {

    @Query("""
            SELECT id,order_id,item_id,quantity,unit,price,sum
            FROM order_item
            WHERE order_id=:orderId
            """)
    Flux<OrderItem> findAllByOrder(Mono<Long> orderId);

    @Query("""
            SELECT id,order_id,item_id,quantity,unit,price,sum
            FROM order_item
            WHERE order_id IN (:orderIds)
            """)
    Flux<OrderItem> findAllByOrders(Flux<Long> orderIds);

}
