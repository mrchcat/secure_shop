package com.github.mrchcat.intershop.item.matcher;

import com.github.mrchcat.intershop.item.domain.Item;
import com.github.mrchcat.intershop.item.dto.ItemDto;
import com.github.mrchcat.intershop.item.dto.NewItemDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

public class ItemMatcher {

    public static Mono<ItemDto> toDto(Mono<Item> item, Mono<Map<Long, Long>> counts) {
        return Mono
                .zip(item, counts)
                .map(tuple -> ItemDto.builder()
                        .id(tuple.getT1().getId())
                        .title(tuple.getT1().getTitle())
                        .description(tuple.getT1().getDescription())
                        .imgPath(tuple.getT1().getImgPath())
                        .price(tuple.getT1().getPrice())
                        .count(tuple.getT2().getOrDefault(tuple.getT1().getId(), 0L))
                        .inCart(tuple.getT2().containsKey(tuple.getT1().getId()))
                        .unit(tuple.getT1().getUnit())
                        .build());
    }

    public static ItemDto toDto(Item item, Map<Long, Long> counts) {
        long itemId = item.getId();
        return ItemDto.builder()
                .id(itemId)
                .title(item.getTitle())
                .description(item.getDescription())
                .imgPath(item.getImgPath())
                .price(item.getPrice())
                .count(counts.getOrDefault(itemId, 0L))
                .inCart(counts.containsKey(itemId))
                .unit(item.getUnit())
                .build();
    }

    public static Flux<ItemDto> toDto(Flux<Item> items, Mono<Map<Long, Long>> counts) {
        var repeatableCounts = counts.repeat();
        return items
                .zipWith(repeatableCounts)
                .map(tuple -> ItemMatcher.toDto(tuple.getT1(), tuple.getT2()));
    }

    public static ItemDto toDto(Item item) {
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
