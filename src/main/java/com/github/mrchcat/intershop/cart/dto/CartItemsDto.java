package com.github.mrchcat.intershop.cart.dto;

import com.github.mrchcat.intershop.item.dto.ItemDto;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Getter
public class CartItemsDto {
    List<ItemDto> itemDtoList;
    BigDecimal total;
    boolean isCartEmpty;
}
