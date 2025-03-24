package com.github.mrchcat.intershop.enums;

public enum CartAction {
    plus(1),
    minus(-1),
    delete(0);

    final int quantity;

    CartAction(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
