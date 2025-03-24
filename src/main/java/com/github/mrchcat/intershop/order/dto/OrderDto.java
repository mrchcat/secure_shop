package com.github.mrchcat.intershop.order.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Getter
public class OrderDto {
    private long id;
    private List<OrderItemDto> orderItems;
    private BigDecimal totalSum;
}
