package com.github.mrchcat.intershop.cart.service;

import com.github.mrchcat.intershop.cart.domain.CartItem;
import com.github.mrchcat.intershop.cart.repository.CartItemRepository;
import com.github.mrchcat.intershop.cart.repository.CartRepository;
import com.github.mrchcat.intershop.item.domain.Item;
import com.github.mrchcat.intershop.order.domain.OrderItem;
import com.github.mrchcat.intershop.order.repository.OrderRepository;
import com.github.mrchcat.intershop.order.service.OrderService;
import com.github.mrchcat.intershop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final UserService userService;
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public Mono<Map<Long, Long>> getCartItemsForUser(long userId) {
        return cartItemRepository
                .findByUserId(userId)
                .collectMap(CartItem::getItemId, CartItem::getQuantity);
    }
//
//    @Override
//    @Transactional
//    public void changeCart(long userId, Item item, CartAction action) {
//        Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new NoSuchElementException("cart not found"));
//        CartItem newCartItem = CartItem.builder()
//                .cart(cart)
//                .item(item)
//                .quantity(1)
//                .build();
//        Set<CartItem> cartItems = cart.getCartItems();
//
//        switch (action) {
//            case plus -> {
//                if (cartItems.contains(newCartItem)) {
//                    CartItem existingCartItem = getCartItem(cartItems, newCartItem);
//                    long oldQuantity = existingCartItem.getQuantity();
//                    existingCartItem.setQuantity(oldQuantity + 1);
//                    cartItems.remove(existingCartItem);
//                    cartItems.add(existingCartItem);
//                } else {
//                    cartItems.add(newCartItem);
//                }
//            }
//            case minus -> {
//                CartItem existingCartItem = getCartItem(cartItems, newCartItem);
//                long oldQuantity = existingCartItem.getQuantity();
//                if (oldQuantity > 1) {
//                    cartItems.remove(existingCartItem);
//                    existingCartItem.setQuantity(oldQuantity - 1);
//                    cartItems.add(existingCartItem);
//                } else {
//                    return;
//                }
//            }
//            case delete -> cartItems.remove(newCartItem);
//        }
//        cartRepository.save(cart);
//    }
//
//    @Override
//    public CartItemsDto getCartItems(long userId) {
//        Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new NoSuchElementException("cart not found"));
//        Set<CartItem> cartItems = cart.getCartItems();
//        if (cartItems.isEmpty()) {
//            return CartItemsDto.builder()
//                    .itemDtoList(Collections.emptyList())
//                    .isCartEmpty(true)
//                    .total(BigDecimal.ZERO)
//                    .build();
//        }
//        BigDecimal total = cartItems.stream()
//                .map(ci -> ci.getItem().getPrice().multiply(BigDecimal.valueOf(ci.getQuantity())))
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//
//        return CartItemsDto.builder()
//                .itemDtoList(CartItemMatcher.toDto(cartItems))
//                .isCartEmpty(false)
//                .total(total)
//                .build();
//    }
//
//    @Override
//    @Transactional
//    public long buyCart(long userId) {
//        Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new NoSuchElementException("cart not found"));
//        if (isCartEmpty(cart)) {
//            throw new RuntimeException("cart is empty");
//        }
//        User user = userService.getUser(userId);
//        Order order = orderService.makeNewOrder(user);
//        Set<CartItem> cartItems = cart.getCartItems();
//
//        HashSet<OrderItem> orderItemHashSet = new HashSet<>();
//        BigDecimal total = BigDecimal.ZERO;
//        for (CartItem ci : cartItems) {
//            long quantity = ci.getQuantity();
//            if (quantity > 0) {
//                Item item = ci.getItem();
//                BigDecimal price = item.getPrice();
//                BigDecimal sum = price.multiply(BigDecimal.valueOf(quantity));
//                OrderItem oi = OrderItem.builder()
//                        .order(order)
//                        .item(item)
//                        .quantity(quantity)
//                        .unit(item.getUnit())
//                        .orderPrice(price)
//                        .sum(sum)
//                        .build();
//                orderItemHashSet.add(oi);
//                total = total.add(sum);
//            }
//        }
//        order.setNumber(generateOrderNumber(order));
//        order.setOrderItems(orderItemHashSet);
//        order.setTotalSum(total);
//        orderRepository.save(order);
//        cartItems.clear();
//        cartRepository.save(cart);
//        return order.getId();
//    }
//
//    private CartItem getCartItem(Set<CartItem> cartItems, CartItem cartItem) {
//        return cartItems.stream()
//                .filter(ci -> ci.equals(cartItem))
//                .findFirst()
//                .orElseThrow(InternalException::new);
//    }
//
//    private String generateOrderNumber(Order order) {
//        LocalDate date = LocalDate.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//        String formattedString = date.format(formatter);
//        return formattedString + "-" + order.getId() + "-" + (int) (Math.random() * 1000);
//    }
//
//    private boolean isCartEmpty(Cart cart) {
//        Set<CartItem> cartItems = cart.getCartItems();
//        if (cartItems.isEmpty()) {
//            return true;
//        }
//        for (CartItem ci : cartItems) {
//            if (ci.getQuantity() > 0) {
//                return false;
//            }
//        }
//        return true;
//    }
}
