package com.github.mrchcat.intershop.item.repository;


import com.github.mrchcat.intershop.item.domain.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface ItemRepository extends ReactiveSortingRepository<Item, Long>, ReactiveCrudRepository<Item, Long> {

    @Query("""
            SELECT id, article_number, name,description,picture_path,price,unit
            FROM items
            WHERE LOWER(name) LIKE LOWER(CONCAT('%',:search,'%'))
               OR LOWER(description) LIKE LOWER(CONCAT('%',:search,'%'))
            """)
    Flux<Item> findAllWithSearch(String search, Pageable pageable);

    Flux<Item> findAllBy(Pageable pageable);

    @Query("""
            SELECT i.id, i.article_number, i.name,i.description,i.picture_path,i.price,i.unit
            FROM items AS i
            JOIN order_item AS oi ON oi.item_id=i.id
            WHERE oi.order_id=:orderId
            """)
    Flux<Item> findAllForOrder(Mono<Long> orderId);


    @Query("""
            SELECT i.id, i.article_number, i.name,i.description,i.picture_path,i.price,i.unit
            FROM items AS i
            JOIN order_item AS oi ON oi.item_id=i.id
            WHERE oi.order_id IN(:orderIds)
            """)
    Flux<Item> findAllForOrders(Flux<Long> orderIds);

}
