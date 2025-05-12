package com.github.mrchcat.intershop.item.controller;

import com.github.mrchcat.intershop.enums.CartAction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ItemPublicControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testMain()  {
        webTestClient.get()
                .uri("/")
                .exchange()
                .expectStatus().is3xxRedirection();
    }

//    @Test
//    void testGetItem() {
//        long itemId = 1;
//        webTestClient.get().uri("/items/" + itemId)
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody(String.class).consumeWith(response -> {
//                    String body = response.getResponseBody();
//                    Assertions.assertNotNull(body);
//                    Assertions.assertTrue(body.contains("Витрина товаров"));
//                });
//    }

//    @Test
//    void testGetItems()  {
//        webTestClient.get()
//                .uri("/main/items")
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody(String.class).consumeWith(response -> {
//                    String body = response.getResponseBody();
//                    Assertions.assertNotNull(body);
//                    Assertions.assertTrue(body.contains("Витрина товаров"));
//                });
//    }

    @Test
    void testUpdateCartPlus() {
        long itemId = 1;
        webTestClient.post().uri(uriBuilder -> uriBuilder
                .path("/main/items/" + itemId)
                .queryParam("action", CartAction.plus.toString())
                .build())
                .exchange()
                .expectStatus().is3xxRedirection();
    }

    @Test
    void testUpdateCartMinus() {
        long itemId = 1;
        webTestClient.post().uri(uriBuilder -> uriBuilder
                        .path("/main/items/" + itemId)
                        .queryParam("action", CartAction.plus.toString())
                        .build())
                .exchange()
                .expectStatus().is3xxRedirection();
        webTestClient.post().uri(uriBuilder -> uriBuilder
                        .path("/main/items/" + itemId)
                        .queryParam("action", CartAction.minus.toString())
                        .build())
                .exchange()
                .expectStatus().is3xxRedirection();
    }

    @Test
    void testUpdateCartDelete()  {
        long itemId = 1;
        webTestClient.post().uri(uriBuilder -> uriBuilder
                        .path("/main/items/" + itemId)
                        .queryParam("action", CartAction.delete.toString())
                        .build())
                .exchange()
                .expectStatus().is3xxRedirection();
    }
}