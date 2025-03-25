package com.github.mrchcat.intershop.item.repository;

import com.github.mrchcat.intershop.item.domain.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ItemRepository extends PagingAndSortingRepository<Item, Long>, CrudRepository<Item, Long> {

    @Query("""
    SELECT i
    FROM Item AS i
    WHERE LOWER(i.title) LIKE LOWER(CONCAT('%',:search,'%'))
       OR LOWER(i.description) LIKE LOWER(CONCAT('%',:search,'%'))
    """)
    Page<Item> findAllWithSearch(String search, Pageable pageable);


}
