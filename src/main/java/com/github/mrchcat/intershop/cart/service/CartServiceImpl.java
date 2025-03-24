package com.github.mrchcat.intershop.cart.service;

import com.github.mrchcat.intershop.cart.domain.Cart;
import com.github.mrchcat.intershop.cart.domain.CartItem;
import com.github.mrchcat.intershop.cart.dto.CartItemsDto;
import com.github.mrchcat.intershop.cart.repository.CartRepository;
import com.github.mrchcat.intershop.enums.CartAction;
import com.github.mrchcat.intershop.enums.Unit;
import com.github.mrchcat.intershop.item.domain.Item;
import com.github.mrchcat.intershop.cart.matcher.CartItemMatcher;
import com.github.mrchcat.intershop.order.domain.Order;
import com.github.mrchcat.intershop.order.domain.OrderItem;
import com.github.mrchcat.intershop.order.repository.OrderRepository;
import com.github.mrchcat.intershop.order.service.OrderService;
import com.github.mrchcat.intershop.user.domain.User;
import com.github.mrchcat.intershop.user.service.UserService;
import com.sun.jdi.InternalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final UserService userService;
    private final OrderService orderService;
    private final OrderRepository orderRepository;

    @Override
    public Map<Item, Long> getCartItemsForUser(long userId) {
        List<Cart> carts = cartRepository.findAllByUserId(userId);
        checkCarts(carts, userId);
        return carts.getFirst()
                .getCartItems()
                .stream()
                .collect(Collectors.toMap(CartItem::getItem, CartItem::getQuantity));
    }


    @Override
    @Transactional
    public void changeCart(long userId, Item item, CartAction action) {
        List<Cart> carts = cartRepository.findAllByUserId(userId);
        checkCarts(carts, userId);
        Cart cart = carts.getFirst();
        CartItem newCartItem = CartItem.builder()
                .cart(cart)
                .item(item)
                .quantity(1)
                .build();
        Set<CartItem> cartItems = cart.getCartItems();

        switch (action) {
            case plus -> {
                if (cartItems.contains(newCartItem)) {
                    CartItem existingCartItem = getCartItem(cartItems, newCartItem);
                    long oldQuantity = existingCartItem.getQuantity();
                    existingCartItem.setQuantity(oldQuantity + 1);
                    cartItems.remove(existingCartItem);
                    cartItems.add(existingCartItem);
                } else {
                    cartItems.add(newCartItem);
                }
            }
            case minus -> {
                CartItem existingCartItem = getCartItem(cartItems, newCartItem);
                long oldQuantity = existingCartItem.getQuantity();
                if (oldQuantity > 0) {
                    cartItems.remove(existingCartItem);
                    existingCartItem.setQuantity(oldQuantity - 1);
                    cartItems.add(existingCartItem);
                } else {
                    return;
                }
            }
            case delete -> cartItems.remove(newCartItem);
        }
        cartRepository.save(cart);
    }

    @Override
    public CartItemsDto getCartItems(long userId) {
        List<Cart> carts = cartRepository.findAllByUserId(userId);
        checkCarts(carts, userId);
        Set<CartItem> cartItems = carts.getFirst().getCartItems();
        if (cartItems.isEmpty()) {
            CartItemsDto build = CartItemsDto.builder()
                    .itemDtoList(Collections.emptyList())
                    .isCartEmpty(true)
                    .total(BigDecimal.ZERO)
                    .build();
            return build;
        }
        BigDecimal total = cartItems.stream()
                .map(ci -> ci.getItem().getPrice().multiply(BigDecimal.valueOf(ci.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return CartItemsDto.builder()
                .itemDtoList(CartItemMatcher.toDto(cartItems))
                .isCartEmpty(false)
                .total(total)
                .build();
    }

    @Override
    @Transactional
    public long buyCart(long userId) {
        User user = userService.getUser(userId);
        Order order = orderService.makeNewOrder(user);
        List<Cart> carts = cartRepository.findAllByUserId(userId);
        checkCarts(carts, userId);

        Cart cart = carts.getFirst();
        Set<CartItem> cartItems = cart.getCartItems();
        if (cartItems.isEmpty()) {
            throw new RuntimeException("cart is empty");
        }
        HashSet<OrderItem> orderItemHashSet = new HashSet<>();
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem ci : cartItems) {
            Item item = ci.getItem();
            long quantity = ci.getQuantity();
            BigDecimal price = item.getPrice();
            BigDecimal sum = price.multiply(BigDecimal.valueOf(quantity));
            OrderItem oi = OrderItem.builder()
                    .order(order)
                    .item(item)
                    .quantity(quantity)
                    .unit(item.getUnit())
                    .orderPrice(price)
                    .sum(sum)
                    .build();
            orderItemHashSet.add(oi);
            total = total.add(sum);
        }
        order.setOrderItems(orderItemHashSet);
        order.setTotalSum(total);
        orderRepository.save(order);
        cartItems.clear();
        cartRepository.save(cart);
        return order.getId();
    }


    private CartItem getCartItem(Set<CartItem> cartItems, CartItem cartItem) {
        return cartItems.stream()
                .filter(ci -> ci.equals(cartItem))
                .findFirst()
                .orElseThrow(InternalException::new);
    }

    private void checkCarts(List<Cart> carts, long userId) {
        if (carts.isEmpty()) {
            throw new InternalException("корзина отсутствует для пользователя " + userId);
        }
        if (carts.size() > 1) {
            throw new UnsupportedOperationException("множественные корзины для пользователя " + userId);
        }
    }

}
