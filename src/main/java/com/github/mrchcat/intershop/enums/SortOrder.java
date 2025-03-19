package com.github.mrchcat.intershop.enums;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

@RequiredArgsConstructor
public enum SortOrder {
    NO(Sort.unsorted()),
    ALPHA(Sort.by("title").ascending()),
    PRICE(Sort.by("price").ascending());

    public final Sort sort;
}
