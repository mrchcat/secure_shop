package com.github.mrchcat.intershop.item.matcher;

import com.github.mrchcat.intershop.item.domain.Item;
import com.github.mrchcat.intershop.item.dto.ItemDto;
import com.github.mrchcat.intershop.item.dto.NewItemDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.Collections;
import java.util.Map;

public class ItemMatcher {

    public static ItemDto toDtoWoCounts(Item item) {
        long itemId = item.getId();
        return ItemDto.builder()
                .id(itemId)
                .title(item.getTitle())
                .description(item.getDescription())
                .imgPath(item.getImgPath())
                .price(item.getPrice())
                .unit(item.getUnit())
                .build();
    }

    private static ItemDto toDto(Item item, Map<Long, Long> counts) {
        ItemDto itemDto = toDtoWoCounts(item);
        long itemId = item.getId();
        itemDto.setCount(counts.getOrDefault(itemId, 0L));
        itemDto.setInCart(counts.containsKey(itemId));
        return itemDto;
    }

    public static Mono<ItemDto> toDto(Mono<Item> item, Mono<Map<Long, Long>> counts) {
        return counts.defaultIfEmpty(Collections.emptyMap())
                .zipWith(item)
                .map(ItemMatcher::tupleToDto);
    }

    public static Flux<ItemDto> toDtos(Flux<Item> items, Mono<Map<Long, Long>> counts) {
        return counts.defaultIfEmpty(Collections.emptyMap()).repeat()
                .zipWith(items)
                .map(ItemMatcher::tupleToDto);
    }

    private static ItemDto tupleToDto(Tuple2<Map<Long, Long>, Item> tuple) {
        if (tuple.getT1().isEmpty()) {
            return ItemMatcher.toDtoWoCounts(tuple.getT2());
        }
        return ItemMatcher.toDto(tuple.getT2(), tuple.getT1());
    }


    public static Item toItem(NewItemDto dto) {
        return Item.builder()
                .articleNumber(dto.getArticleNumber())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .imgPath(dto.getImgPath())
                .price(dto.getPrice())
                .unit(dto.getUnit())
                .build();
    }

}
