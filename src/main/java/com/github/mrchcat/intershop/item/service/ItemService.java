package com.github.mrchcat.intershop.item.service;

import com.github.mrchcat.intershop.enums.SortOrder;
import com.github.mrchcat.intershop.item.domain.Item;
import com.github.mrchcat.intershop.item.dto.ItemDto;
import com.github.mrchcat.intershop.item.dto.MainItemsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemService {

    ItemDto getItem(long userId, long itemId);

    MainItemsDto getItems(long userId, String search, Pageable pageable);

}
