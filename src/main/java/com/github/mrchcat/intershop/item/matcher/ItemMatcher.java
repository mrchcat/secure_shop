package com.github.mrchcat.intershop.item.matcher;

import com.github.mrchcat.intershop.item.domain.Item;
import com.github.mrchcat.intershop.item.dto.ItemDto;

import java.util.List;
import java.util.Map;

public class ItemMatcher {

    public static ItemDto toDto(Item item, Map<Item, Long> counts) {
        return ItemDto.builder()
                .title(item.getTitle())
                .description(item.getDescription())
                .price(item.getPrice())
                .imgPath(item.getImgPath())
                .count(counts.getOrDefault(item, 0L))
                .unit(item.getUnit())
                .build();
    }

    public static List<ItemDto> toDto(List<Item> item, Map<Item, Long> counts) {
        return item.stream()
                .map(i -> ItemMatcher.toDto(i, counts))
                .toList();
    }

}
