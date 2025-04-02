package com.github.mrchcat.intershop.item.dto;

import com.github.mrchcat.intershop.enums.Unit;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;


@Builder
@Getter
@ToString
public class ItemDto {

    private long id;
    private String title;
    private String description;
    private String imgPath;
    private BigDecimal price;
    Mono<Long> count;
    Mono<Boolean> inCart;
    private Unit unit;

}
