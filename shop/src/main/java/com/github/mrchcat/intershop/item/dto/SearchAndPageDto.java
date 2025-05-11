package com.github.mrchcat.intershop.item.dto;

import com.github.mrchcat.intershop.enums.SortOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchAndPageDto {
    private String search = "";
    private SortOrder sort = SortOrder.NO;
    private int pageSize = 10;
    private int pageNumber = 0;
}

