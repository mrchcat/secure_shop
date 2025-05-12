package com.github.mrchcat.intershop.item.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.github.mrchcat.intershop.enums.Unit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(include = JsonTypeInfo.As.PROPERTY, use = JsonTypeInfo.Id.NAME)
public class ItemDto {

    private long id;
    private String title;
    private String description;
    private String imgPath;
    private BigDecimal price;
    long count;
    boolean inCart;
    private Unit unit;
}
