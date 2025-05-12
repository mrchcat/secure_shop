package com.github.mrchcat.intershop.cart.controller;

import com.github.mrchcat.intershop.enums.CartAction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class CartControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
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
    void testUpdateCartPlus()  {
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
    void testUpdateCartMinus()  {
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
    void testUpdateCartDelete() {
        long itemId = 1;
        webTestClient.post().uri(uriBuilder -> uriBuilder.path("/cart/items/" + itemId)
                        .queryParam("action", CartAction.delete.toString())
                        .build())
                .exchange()
                .expectStatus().is3xxRedirection();
    }

//    @Test
//    void testBuy()  {
//        long itemId = 1;
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