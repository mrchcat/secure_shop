package com.github.mrchcat.intershop.item.dto;

import com.github.mrchcat.intershop.item.domain.Item;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;
import reactor.core.publisher.Flux;

import java.util.List;

@Builder
@Getter
public class MainItemsDto {
    private Flux<List<ItemDto>> items;
    private Page<Item> page;
}
