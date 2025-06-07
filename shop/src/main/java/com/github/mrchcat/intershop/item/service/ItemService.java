package com.github.mrchcat.intershop.item.service;

import com.github.mrchcat.intershop.enums.CartAction;
import com.github.mrchcat.intershop.item.domain.Item;
import com.github.mrchcat.intershop.item.dto.ItemDto;
import com.github.mrchcat.intershop.item.dto.NewItemDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    String ITEM = "item";
    String PAGE_ITEM = "itemPage";

    Mono<ItemDto> getItemDto(Optional<Long> userId, long itemId);

    Mono<Page<List<ItemDto>>> getItemDtos(Optional<Long> userId, Pageable pageable, String search);

    Mono<Void> changeCart(long userId, long itemId, CartAction action);

    Flux<Item> getItemsForOrders(Flux<Long> orderIds);

    Flux<Item> getItemsForOrder(Mono<Long> orderId);

    Mono<Void> downloadNewItem(Mono<NewItemDto> itemDto);

}
