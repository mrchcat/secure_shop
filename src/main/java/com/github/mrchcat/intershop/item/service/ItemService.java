package com.github.mrchcat.intershop.item.service;

import com.github.mrchcat.intershop.item.dto.ItemDto;
import reactor.core.publisher.Mono;

public interface ItemService {

    Mono<ItemDto> getItem(long userId, long itemId);

//    Mono<MainItemsDto> getItems(long userId, String search, Pageable pageable);
//
//    Mono<Void> changeCart(long userId, long itemId, CartAction action);
//
//    Mono<Void> downloadNewItem(NewItemDto itemDto);

}
