package com.github.mrchcat.intershop.item.service;

import com.github.mrchcat.intershop.enums.SortOrder;
import com.github.mrchcat.intershop.item.domain.Item;
import com.github.mrchcat.intershop.item.dto.MainItemsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemService {

    Item getItem(long itemId);

    MainItemsDto getItems(String search, Pageable pageable);

}
