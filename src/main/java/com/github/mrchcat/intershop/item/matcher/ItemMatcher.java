package com.github.mrchcat.intershop.item.matcher;

import com.github.mrchcat.intershop.item.domain.Item;
import com.github.mrchcat.intershop.item.dto.ItemDto;
import reactor.core.publisher.Mono;

import java.util.Map;

public class ItemMatcher {

    public static Mono<ItemDto> toDto(Mono<Item> item, Mono<Map<Long, Long>> counts) {
        return item.map(it -> ItemDto.builder()
                        .id(it.getId())
                        .title(it.getTitle())
                        .description(it.getDescription())
                        .imgPath(it.getImgPath())
                        .price(it.getPrice())
                        .count(counts.map(m->m.getOrDefault(it.getId(),0L)))
                        .inCart(counts.map(m->m.containsKey(it.getId())))
                        .unit(it.getUnit())
                        .build()
        );
    }


//    public static ItemDto toDto(Item item, Map<Item, Long> counts) {
//        return ItemDto.builder()
//                .id(item.getId())
//                .title(item.getTitle())
//                .description(item.getDescription())
//                .price(item.getPrice())
//                .imgPath(item.getImgPath())
//                .inCart(counts.containsKey(item))
//                .count(counts.getOrDefault(item, 0L))
//                .unit(item.getUnit())
//                .build();
//    }
//
//    public static List<ItemDto> toDto(List<Item> item, Map<Item, Long> counts) {
//        return item.stream()
//                .map(i -> ItemMatcher.toDto(i, counts))
//                .toList();
//    }
//
//    public static ItemDto toDto(Item item, boolean inCart, long counts) {
//        return ItemDto.builder()
//                .id(item.getId())
//                .title(item.getTitle())
//                .description(item.getDescription())
//                .price(item.getPrice())
//                .imgPath(item.getImgPath())
//                .inCart(inCart)
//                .count(counts)
//                .unit(item.getUnit())
//                .build();
//    }
//
//    public static ItemDto toDto(Item item) {
//        return ItemDto.builder()
//                .id(item.getId())
//                .title(item.getTitle())
//                .description(item.getDescription())
//                .price(item.getPrice())
//                .imgPath(item.getImgPath())
//                .unit(item.getUnit())
//                .build();
//    }
//
//    public static Item toItem(NewItemDto dto){
//        return Item.builder()
//                .articleNumber(dto.getArticleNumber())
//                .title(dto.getTitle())
//                .description(dto.getDescription())
//                .imgPath(dto.getImgPath())
//                .price(dto.getPrice())
//                .unit(dto.getUnit())
//                .build();
//    }


}
