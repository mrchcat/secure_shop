package com.github.mrchcat.intershop.item.service;

import com.github.mrchcat.intershop.item.domain.Item;
import com.github.mrchcat.intershop.item.dto.MainItemsDto;
import com.github.mrchcat.intershop.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Setter
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    @Value("${application.items.perline:3}")
    private int itemsPerLine;

    @Override
    public Item getItem(long itemId) {
        return itemRepository
                .findById(itemId)
                .orElseThrow(() -> new NoSuchElementException(String.format("товар c id=%s не найден", itemId)));
    }

    @Override
    public MainItemsDto getItems(String search, Pageable pageable) {
        Page<Item> itemPage = (search.isBlank())
                ? itemRepository.findAll(pageable)
                : itemRepository.findAllWithSearch(search, pageable);
        List<Item> itemList = itemPage.getContent();

        List<List<Item>> itemsToShow = new ArrayList<>();
        for (int i = 0; i < itemList.size(); i = i + 3) {
            itemsToShow.add(itemList.subList(i, i + 3));
        }
        return MainItemsDto.builder()
                .items(itemsToShow)
                .page(itemPage)
                .build();
    }
}
