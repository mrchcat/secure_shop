package com.github.mrchcat.intershop.item.dto;

import com.github.mrchcat.intershop.enums.Unit;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;


@Builder
@Getter

public class ItemDto {

    private long id;
    private String title;
    private String description;
    private String imgPath;
    private BigDecimal price;
    private long count;
    boolean inCart;
    private Unit unit;

}
