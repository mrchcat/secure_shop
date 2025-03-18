package com.github.mrchcat.intershop.product.repository;

import com.github.mrchcat.intershop.product.domain.Item;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ItemRepository extends PagingAndSortingRepository<Item, Long> {
}
