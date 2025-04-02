package com.github.mrchcat.intershop.item.repository;


import com.github.mrchcat.intershop.item.domain.Item;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Mono;

import java.awt.print.Pageable;

public interface ItemRepository extends ReactiveSortingRepository<Item, Long>, ReactiveCrudRepository<Item, Long> {

    @Query("""
            SELECT i
            FROM Item AS i
            WHERE LOWER(i.title) LIKE LOWER(CONCAT('%',:search,'%'))
               OR LOWER(i.description) LIKE LOWER(CONCAT('%',:search,'%'))
            """)
    Mono<Item> findAllWithSearch(String search, Pageable pageable);


}
