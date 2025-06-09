package com.github.mrchcat.intershop.cart.controller;

import com.github.mrchcat.dto.Payment;
import com.github.mrchcat.intershop.AbstractTestContainerTest;
import com.github.mrchcat.intershop.enums.CartAction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;

public class CartControllerTest extends AbstractTestContainerTest {

    @Test
    @WithUserDetails("admin")
    void testGetCart() {
        webTestClient.get()
                .uri("/cart/items")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.TEXT_HTML)
                .expectBody(String.class).consumeWith(response -> {
                    String body = response.getResponseBody();
                    Assertions.assertNotNull(body);
                    Assertions.assertTrue(body.contains("Корзина товаров"));
                });
    }

    @Test
    @WithUserDetails("admin")
    void testUpdateCartPlus() {
        long itemId = 1;
        webTestClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/cart/items/" + itemId)
                        .queryParam("action", CartAction.plus.toString())
                        .build())
                .exchange()
                .expectStatus().is3xxRedirection();

    }

    @Test
    @WithUserDetails("admin")
    void testUpdateCartMinus() {
        long itemId = 1;
        webTestClient.post().uri(uriBuilder -> uriBuilder.path("/cart/items/" + itemId)
                        .queryParam("action", CartAction.plus.toString())
                        .build())
                .exchange()
                .expectStatus().is3xxRedirection();

        webTestClient.post().uri(uriBuilder -> uriBuilder.path("/cart/items/" + itemId)
                        .queryParam("action", CartAction.minus.toString())
                        .build())
                .exchange()
                .expectStatus().is3xxRedirection();
    }

    @Test
    @WithUserDetails("admin")
    void testUpdateCartDelete() {
        long itemId = 1;
        webTestClient.post().uri(uriBuilder -> uriBuilder.path("/cart/items/" + itemId)
                        .queryParam("action", CartAction.delete.toString())
                        .build())
                .exchange()
                .expectStatus().is3xxRedirection();
    }

    @Test
    @WithUserDetails("admin")
    void testBuy() {
        Payment payment = Payment.builder()
                .paymentId(UUID.randomUUID())
                .payer(UUID.randomUUID())
                .recipient(UUID.randomUUID())
                .amount(BigDecimal.TEN)
                .build();

        long itemId = 2;
        Mockito.when(paymentClient.createPayment(Mockito.any(),Mockito.any())).thenReturn(Mono.just(payment));

        webTestClient.post().uri(uriBuilder -> uriBuilder.path("/cart/items/" + itemId)
                        .queryParam("action", CartAction.plus.toString())
                        .build())
                .exchange()
                .expectStatus().is3xxRedirection();

        webTestClient.post().uri("/buy")
                .exchange()
                .expectStatus().is3xxRedirection();
    }

}