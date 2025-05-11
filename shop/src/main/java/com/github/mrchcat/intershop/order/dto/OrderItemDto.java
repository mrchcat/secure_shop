package com.github.mrchcat.intershop.order.dto;

import com.github.mrchcat.intershop.enums.Unit;
import com.github.mrchcat.intershop.item.dto.ItemDto;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Builder
@Getter
@ToString

public class OrderItemDto {

    private ItemDto item;
    private long count;
    private Unit unit;
    private BigDecimal sum;

}
