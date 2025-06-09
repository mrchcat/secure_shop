package com.github.mrchcat.intershop.item.controller;

import com.github.mrchcat.intershop.AbstractTestContainerTest;
import com.github.mrchcat.intershop.enums.CartAction;
import com.github.mrchcat.intershop.item.service.ItemService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.test.context.support.WithUserDetails;

class ItemPublicControllerTest extends AbstractTestContainerTest {

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    ItemService itemService;


    @Test
    @WithUserDetails("admin")
    void testMain() {
        webTestClient.get()
                .uri("/")
                .exchange()
                .expectStatus().is3xxRedirection();
    }

    @Test
    @WithUserDetails("admin")
    void testGetItem() {
        long itemId = 1;
        webTestClient.get()
                .uri("/items/" + itemId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).consumeWith(response -> {
                    String body = response.getResponseBody();
                    Assertions.assertNotNull(body);
                    Assertions.assertTrue(body.contains("Витрина товаров"));
                });
    }

    @Test
    @WithUserDetails("admin")
    void testGetItems() {
        webTestClient.get()
                .uri("/main/items")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).consumeWith(response -> {
                    String body = response.getResponseBody();
                    Assertions.assertNotNull(body);
                    Assertions.assertTrue(body.contains("Витрина товаров"));
                });
    }

    @Test
    @WithUserDetails("admin")
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
    @WithUserDetails("admin")
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
    @WithUserDetails("admin")
    void testUpdateCartDelete() {
        long itemId = 1;
        webTestClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/main/items/" + itemId)
                        .queryParam("action", CartAction.delete.toString())
                        .build()
                )
                .exchange()
                .expectStatus().is3xxRedirection();
    }

    @Test
    @WithUserDetails("admin")
    void testGetItemRepeatedly() {
        long itemId = 1;
        webTestClient.get().uri("/items/" + itemId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).consumeWith(response -> {
                    String body = response.getResponseBody();
                    Assertions.assertNotNull(body);
                    Assertions.assertTrue(body.contains("Витрина товаров"));
                });
        webTestClient.get().uri("/items/" + itemId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).consumeWith(response -> {
                    String body = response.getResponseBody();
                    Assertions.assertNotNull(body);
                    Assertions.assertTrue(body.contains("Витрина товаров"));
                });
        Assertions.assertTrue(redisTemplate.hasKey(ItemService.ITEM + "::" + itemId));
    }


}