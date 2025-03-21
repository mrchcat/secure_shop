package com.github.mrchcat.intershop.order.service;

import com.github.mrchcat.intershop.item.domain.Item;

import java.util.Map;

public interface OrderService {

    Map<Item, Long> getBasketCountsForUser(long userId);

}
