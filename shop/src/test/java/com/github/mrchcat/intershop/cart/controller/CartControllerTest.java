package com.github.mrchcat.intershop.cart.controller;

import com.github.mrchcat.intershop.AbstractTestContainerTest;

public class CartControllerTest extends AbstractTestContainerTest {
//
//    @Test
//    void testGetCart() {
//        webTestClient.get()
//                .uri("/cart/items")
//                .exchange()
//                .expectStatus().isOk()
//                .expectHeader().contentType(MediaType.TEXT_HTML)
//                .expectBody(String.class).consumeWith(response -> {
//                    String body = response.getResponseBody();
//                    Assertions.assertNotNull(body);
//                    Assertions.assertTrue(body.contains("Корзина товаров"));
//                });
//    }
//
//    @Test
//    void testUpdateCartPlus() {
//        long itemId = 1;
//        webTestClient.post()
//                .uri(uriBuilder -> uriBuilder
//                        .path("/cart/items/" + itemId)
//                        .queryParam("action", CartAction.plus.toString())
//                        .build())
//                .exchange()
//                .expectStatus().is3xxRedirection();
//
//    }
//
//    @Test
//    void testUpdateCartMinus() {
//        long itemId = 1;
//        webTestClient.post().uri(uriBuilder -> uriBuilder.path("/cart/items/" + itemId)
//                        .queryParam("action", CartAction.plus.toString())
//                        .build())
//                .exchange()
//                .expectStatus().is3xxRedirection();
//
//        webTestClient.post().uri(uriBuilder -> uriBuilder.path("/cart/items/" + itemId)
//                        .queryParam("action", CartAction.minus.toString())
//                        .build())
//                .exchange()
//                .expectStatus().is3xxRedirection();
//    }
//
//    @Test
//    void testUpdateCartDelete() {
//        long itemId = 1;
//        webTestClient.post().uri(uriBuilder -> uriBuilder.path("/cart/items/" + itemId)
//                        .queryParam("action", CartAction.delete.toString())
//                        .build())
//                .exchange()
//                .expectStatus().is3xxRedirection();
//    }
//
//    @Test
//    void testBuy() {
//        Payment payment = Payment.builder()
//                .paymentId(UUID.randomUUID())
//                .payer(UUID.randomUUID())
//                .recipient(UUID.randomUUID())
//                .amount(BigDecimal.TEN)
//                .build();
//
//        Mockito.when(paymentClient.createPayment(Mockito.any())).thenReturn(Mono.just(payment));
//
//        long itemId = 2;
//        webTestClient.post().uri(uriBuilder -> uriBuilder.path("/cart/items/" + itemId)
//                        .queryParam("action", CartAction.plus.toString())
//                        .build())
//                .exchange()
//                .expectStatus().is3xxRedirection();
//
//        webTestClient.post().uri("/buy")
//                .exchange()
//                .expectStatus().is3xxRedirection();
//    }

}