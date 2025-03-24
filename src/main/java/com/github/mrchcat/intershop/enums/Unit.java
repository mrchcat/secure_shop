package com.github.mrchcat.intershop.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Unit {
    PIECE("шт"), SQUARE_METRE("кв.м");

    public final String name;

}
