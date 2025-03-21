package com.github.mrchcat.intershop.order.service;

import com.github.mrchcat.intershop.item.domain.Item;
import com.github.mrchcat.intershop.order.domain.OrderItem;
import com.github.mrchcat.intershop.user.domain.User;
import com.github.mrchcat.intershop.user.service.UserService;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    private final UserService userService;

    @Override
    public Map<Item, Long> getBasketCountsForUser(long userId) {
        User user = userService.getUser(userId);
        Set<OrderItem> oiSet = user.getBasket().getOrderItems();
        if (oiSet.isEmpty()) {
            return Collections.emptyMap();
        }
        return oiSet.stream()
                .collect(Collectors.toMap(OrderItem::getItem, OrderItem::getQuantity));
    }
}
