package com.github.mrchcat.intershop.order.service;

import com.github.mrchcat.intershop.order.domain.Order;
import com.github.mrchcat.intershop.order.dto.OrderDto;
import com.github.mrchcat.intershop.user.domain.User;

import java.util.List;

public interface OrderService {
    Order makeNewOrder(User user);

    List<OrderDto> getOrders(long userId);

    OrderDto getOrder(long userId, long orderId);
}
