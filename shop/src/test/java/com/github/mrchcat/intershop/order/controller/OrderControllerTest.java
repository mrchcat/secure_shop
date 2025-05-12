package com.github.mrchcat.intershop.order.controller;

import com.github.mrchcat.intershop.order.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class OrderControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    OrderRepository orderRepository;

    @Test
    void testGetOrders() {
        webTestClient.get().uri("/orders")
                .exchange()
                .expectStatus().isOk();
    }

//    @Test
//    void testGetOrder() {
//        long itemId = 1;
//        long userId = 1;
//        webTestClient.post().uri(uriBuilder -> uriBuilder
//                        .path("/cart/items/" + itemId)
//                        .queryParam("action", CartAction.plus.toString())
//                        .build())
//                .exchange()
//                .expectStatus().is3xxRedirection();
//
//        webTestClient.post()
//                .uri("/buy")
//                .exchange()
//                .expectStatus().is3xxRedirection();
//
//        Mono<Long> orderId = orderRepository.findAllByUserId(userId)
//                .elementAt(1)
//                .map(Order::getId);
//
//        webTestClient.get().uri("/orders/" + orderId)
//                .exchange()
//                .expectStatus();
//    }
}