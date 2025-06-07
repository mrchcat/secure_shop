package com.github.mrchcat.intershop.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PayServiceError {
    NO(""),
    NOT_ENOUGH_MONEY("К сожалению, средств не достаточно для оплаты заказа"),
    OUT_OF_ORDER("К сожалению, сервис в настоящее время недоступен. Попробуйте позднее."),
    NO_SUCH_CLIENT("К сожалению, клиент не найден. Проверьте, что платежный сервис содержит корректный UUID клиента");


    private final String message;
}
