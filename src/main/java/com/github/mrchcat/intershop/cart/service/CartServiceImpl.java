package com.github.mrchcat.intershop.cart.service;

import com.github.mrchcat.intershop.cart.domain.Cart;
import com.github.mrchcat.intershop.cart.domain.CartItem;
import com.github.mrchcat.intershop.cart.domain.CartItemPrice;
import com.github.mrchcat.intershop.cart.dto.CartItemsDto;
import com.github.mrchcat.intershop.cart.matcher.CartItemPriceMatcher;
import com.github.mrchcat.intershop.cart.repository.CartItemPriceRepository;
import com.github.mrchcat.intershop.cart.repository.CartItemRepository;
import com.github.mrchcat.intershop.cart.repository.CartRepository;
import com.github.mrchcat.intershop.enums.CartAction;
import com.github.mrchcat.intershop.item.domain.Item;
import com.github.mrchcat.intershop.order.domain.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartItemPriceRepository cartItemPriceRepository;

    @Override
    public Mono<Cart> getCartForUser(long userId) {
        return cartRepository
                .findByUserId(userId)
                .switchIfEmpty(Mono.error(new NoSuchElementException(
                        String.format("корзина для пользователя id=%s не найден", userId))));
    }

    @Override
    public Mono<Map<Long, Long>> getCartItemsForUser(long userId) {
        Mono<Cart> cart = cartRepository.findByUserId(userId)
                .switchIfEmpty(Mono.error(new NoSuchElementException(
                        String.format("корзина для пользователя id=%s не найден", userId))));
        return cartItemRepository
                .findByCart(cart.map(Cart::getId))
                .collectMap(CartItem::getItemId, CartItem::getQuantity);
    }

    @Override
    @Transactional
    public Mono<Void> changeCart(long userId, Mono<Item> item, CartAction action) {
        Mono<Cart> cart = cartRepository
                .findByUserId(userId)
                .switchIfEmpty(Mono.error(new NoSuchElementException(
                        String.format("корзина для пользователя id=%s не найден", userId))));
        Mono<CartItem> cartItem = cartItemRepository.findByCartAndItem(cart.map(Cart::getId), item.map(Item::getId));
        return switch (action) {
            case plus -> cartItem
                    .switchIfEmpty(Mono
                            .zip(cart, item)
                            .map(tuple -> CartItem.builder()
                                    .cartId(tuple.getT1().getId())
                                    .itemId(tuple.getT2().getId())
                                    .build()))
                    .map(ci -> this.applyAction(ci, action))
                    .flatMap(cartItemRepository::save)
                    .then();

            case minus -> cartItem
                    .switchIfEmpty(Mono.error(new NoSuchElementException(
                            String.format("товар с id=%s не найден ", item.map(Item::getId)))))
                    .map(ci -> this.applyAction(ci, action))
                    .flatMap(cartItemRepository::save)
                    .then();
            case delete -> cartItemRepository.deleteByCartAndItem(cart.map(Cart::getId), item.map(Item::getId));
        };
    }

    private CartItem applyAction(CartItem cartItem, CartAction action) {
        cartItem.setQuantity(Math.max(0, cartItem.getQuantity() + action.delta));
        return cartItem;
    }

    @Override
    public Mono<CartItemsDto> getCartItems(long userId) {
        Mono<Cart> cart = cartRepository
                .findByUserId(userId)
                .switchIfEmpty(Mono.error(new NoSuchElementException(
                        String.format("корзина для пользователя id=%s не найден", userId))));
        Flux<CartItemPrice> cartItemPrices = cartItemPriceRepository.findByCart(cart.map(Cart::getId));

        return cartItemPrices
                .collectList()
                .map(list -> {
                    if (list.isEmpty()) {
                        return CartItemsDto.builder()
                                .itemDtoList(Collections.emptyList())
                                .isCartEmpty(true)
                                .total(BigDecimal.ZERO)
                                .build();
                    }
                    BigDecimal total = list.stream()
                            .map(cip -> cip.getPrice().multiply(BigDecimal.valueOf(cip.getQuantity())))
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    return CartItemsDto.builder()
                            .itemDtoList(CartItemPriceMatcher.toDto(list))
                            .isCartEmpty(false)
                            .total(total)
                            .build();
                });
    }

    @Override
    public Flux<OrderItem> getOrderItemsForCart(Mono<Cart> cart) {
        return cartItemPriceRepository
                .findByCart(cart.map(Cart::getId))
                .filter(cip -> cip.getQuantity() > 0)
                .map(cip -> {
                    long quantity = cip.getQuantity();
                    BigDecimal price = cip.getPrice();
                    BigDecimal sum = price.multiply(BigDecimal.valueOf(quantity));
                    return OrderItem.builder()
                            .itemId(cip.getItemId())
                            .quantity(quantity)
                            .unit(cip.getUnit())
                            .orderPrice(price)
                            .sum(sum)
                            .build();
                });
    }

    @Override
    public Mono<Void> clearCart(Mono<Cart> cart) {
        return cart.map(Cart::getId)
                .flatMap(cartItemRepository::clearCart);
    }
}
