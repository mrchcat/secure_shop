package com.github.mrchcat.intershop.item.service;

import com.github.mrchcat.intershop.enums.CartAction;
import com.github.mrchcat.intershop.item.domain.Item;
import com.github.mrchcat.intershop.item.dto.ItemDto;
import com.github.mrchcat.intershop.item.dto.NewItemDto;
import com.github.mrchcat.intershop.item.dto.PageWrapper;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ItemService {
    String ITEM_DTO = "itemDto";
    String PAGE_ITEM_DTO = "itemDtoPage";

    Mono<ItemDto> getItem(long userId, long itemId);

    Mono<PageWrapper> getItems(long userId, Pageable pageable, String search);

    Mono<Void> changeCart(long userId, long itemId, CartAction action);

    Flux<Item> getItemsForOrders(Flux<Long> orderIds);

    Flux<Item> getItemsForOrder(Mono<Long> orderId);

    Mono<Void> downloadNewItem(Mono<NewItemDto> itemDto);

}
