package com.github.mrchcat.intershop.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CartAction {
    plus(1), minus(-1), delete(0);

    public final int delta;
}
