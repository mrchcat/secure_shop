package com.github.mrchcat.intershop.order.controller;

import com.github.mrchcat.intershop.AbstractTestContainerTest;

class OrderControllerTest extends AbstractTestContainerTest {
//
//    @Autowired
//    OrderRepository orderRepository;
//
//    @Test
//    void testGetOrders() {
//        webTestClient.get().uri("/orders")
//                .exchange()
//                .expectStatus().isOk();
//    }
//
//    @Test
//    void testGetOrder() {
//        long itemId = 1;
//        long userId = 1;
//
//        Payment payment = Payment.builder()
//                .paymentId(UUID.randomUUID())
//                .payer(UUID.randomUUID())
//                .recipient(UUID.randomUUID())
//                .amount(BigDecimal.TEN)
//                .build();
//
//        Mockito.when(paymentClient.createPayment(Mockito.any())).thenReturn(Mono.just(payment));
//
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