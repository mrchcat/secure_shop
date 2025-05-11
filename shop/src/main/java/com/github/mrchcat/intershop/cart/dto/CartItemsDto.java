package com.github.mrchcat.intershop.cart.dto;

import com.github.mrchcat.intershop.enums.PayServiceError;
import com.github.mrchcat.intershop.item.dto.ItemDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Getter
@Setter
public class CartItemsDto {
    private List<ItemDto> itemDtoList;
    private BigDecimal total;
    private boolean isCartEmpty;
    private boolean enablePayment;
    private PayServiceError payError;
}
