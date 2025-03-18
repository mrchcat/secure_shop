package com.github.mrchcat.intershop.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Unit {
    PIECE("шт"), SQUARE_METRE("кв.м");

    public final String name;
}
