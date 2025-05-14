package com.github.mrchcat.intershop.cart.service;

import com.github.mrchcat.client.PaymentClient;
import com.github.mrchcat.dto.Balance;
import com.github.mrchcat.intershop.cart.domain.Cart;
import com.github.mrchcat.intershop.cart.domain.CartItem;
import com.github.mrchcat.intershop.cart.domain.CartItemPrice;
import com.github.mrchcat.intershop.cart.dto.CartItemsDto;
import com.github.mrchcat.intershop.cart.matcher.CartItemPriceMatcher;
import com.github.mrchcat.intershop.cart.repository.CartItemPriceRepository;
import com.github.mrchcat.intershop.cart.repository.CartItemRepository;
import com.github.mrchcat.intershop.cart.repository.CartRepository;
import com.github.mrchcat.intershop.enums.CartAction;
import com.github.mrchcat.intershop.enums.PayServiceError;
import com.github.mrchcat.intershop.item.domain.Item;
import com.github.mrchcat.intershop.order.domain.OrderItem;
import com.github.mrchcat.intershop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.logging.Level;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartItemPriceRepository cartItemPriceRepository;
    private final UserService userService;
    private final PaymentClient paymentClient;

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
        Mono<CartItem> cartItem = cartItemRepository.findByCartAndItem(cart.map(Cart::getId), item.map(Item::getId))
                .log("КартАйтем", Level.INFO, SignalType.ON_COMPLETE, SignalType.ON_ERROR);
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
        Mono<Balance> balance = userService.getUser(userId)
                .flatMap(user -> paymentClient.getBalance(user.getPaymentId()))
                .onErrorReturn(new Balance(null, null, BigDecimal.ZERO, ""));

        return cartItemPrices
                .collectList()
                .zipWith(balance)
                .map(tuple -> {
                    List<CartItemPrice> list = tuple.getT1();
                    if (list.isEmpty()) {
                        return CartItemsDto.builder()
                                .itemDtoList(Collections.emptyList())
                                .isCartEmpty(true)
                                .total(BigDecimal.ZERO)
                                .enablePayment(false)
                                .payError(PayServiceError.NO)
                                .build();
                    }
                    BigDecimal totalAmount = list.stream()
                            .map(cip -> cip.getPrice().multiply(BigDecimal.valueOf(cip.getQuantity())))
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    if (tuple.getT2().getClient() == null && tuple.getT2().getAccount() == null) {
                        return CartItemsDto.builder()
                                .itemDtoList(CartItemPriceMatcher.toDto(list))
                                .isCartEmpty(false)
                                .total(totalAmount)
                                .enablePayment(false)
                                .payError(PayServiceError.OUT_OF_ORDER)
                                .build();
                    }
                    BigDecimal userBalance = tuple.getT2().getAmount();
                    boolean isEnoughMoney = userBalance.compareTo(totalAmount) >= 0;
                    return CartItemsDto.builder()
                            .itemDtoList(CartItemPriceMatcher.toDto(list))
                            .isCartEmpty(false)
                            .total(totalAmount)
                            .enablePayment(isEnoughMoney)
                            .payError(isEnoughMoney ? PayServiceError.NO : PayServiceError.NOT_ENOUGH_MONEY)
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
