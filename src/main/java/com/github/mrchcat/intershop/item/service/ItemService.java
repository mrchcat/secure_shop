package com.github.mrchcat.intershop.item.service;

import com.github.mrchcat.intershop.enums.CartAction;
import com.github.mrchcat.intershop.item.dto.ItemDto;
import com.github.mrchcat.intershop.item.dto.MainItemsDto;
import org.springframework.data.domain.Pageable;

public interface ItemService {

    ItemDto getItem(long userId, long itemId);

    MainItemsDto getItems(long userId, String search, Pageable pageable);

    void changeCart(long userId, long itemId, CartAction action);

}
