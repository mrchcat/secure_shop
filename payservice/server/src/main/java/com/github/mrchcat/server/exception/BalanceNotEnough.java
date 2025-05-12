package com.github.mrchcat.server.exception;

public class BalanceNotEnough extends RuntimeException {
    public BalanceNotEnough(String message) {
        super(message);
    }
}
