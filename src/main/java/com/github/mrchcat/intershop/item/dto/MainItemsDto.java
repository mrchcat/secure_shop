package com.github.mrchcat.intershop.item.dto;

import com.github.mrchcat.intershop.item.domain.Item;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
@Getter
public class MainItemsDto {
    private List<List<ItemDto>> items;
    private Page<ItemDto> page;
}
