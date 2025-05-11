package com.github.mrchcat.intershop.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Getter
@Setter
@ToString
public class OrderDto {
    private long id;
    private List<OrderItemDto> orderItems;
    private BigDecimal totalSum;
}
