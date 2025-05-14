package com.github.mrchcat.intershop.item.repository;

import com.github.mrchcat.intershop.item.domain.Item;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.github.mrchcat.intershop.item.service.ItemService.ITEM;
import static com.github.mrchcat.intershop.item.service.ItemService.PAGE_ITEM;

public interface ItemCachedRepository extends ReactiveCrudRepository<Item, Long> {


    @Cacheable(value = ITEM, key = "#itemId")
    Mono<Item> findById(long itemId);

    @Query("""
            SELECT id, article_number, name,description,picture_path,price,unit
            FROM items
            WHERE LOWER(name) LIKE LOWER(CONCAT('%',:search,'%'))
               OR LOWER(description) LIKE LOWER(CONCAT('%',:search,'%'))
            """)
    @Cacheable(value = PAGE_ITEM, key = "{#pageable,#search}")
    Flux<Item> findAllWithSearch(String search, Pageable pageable);

    @Cacheable(value = PAGE_ITEM, key = "#pageable")
    Flux<Item> findAllBy(Pageable pageable);

}
