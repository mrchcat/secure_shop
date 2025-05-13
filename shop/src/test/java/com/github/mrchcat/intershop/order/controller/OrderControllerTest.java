package com.github.mrchcat.intershop.order.controller;

import com.github.mrchcat.client.PaymentClient;
import com.github.mrchcat.dto.Payment;
import com.github.mrchcat.intershop.AbstractTestContainerTest;
import com.github.mrchcat.intershop.enums.CartAction;
import com.github.mrchcat.intershop.order.domain.Order;
import com.github.mrchcat.intershop.order.repository.OrderRepository;
import com.redis.testcontainers.RedisContainer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Testcontainers
@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Container
    @ServiceConnection
    public final static PostgreSQLContainer postgres=new PostgreSQLContainer<>(DockerImageName.parse("postgres:17.4"));

    @Container
    @ServiceConnection
    public final static RedisContainer redis=new RedisContainer(DockerImageName.parse("redis:latest")).withExposedPorts(6379);;

    @Autowired
    public  WebTestClient webTestClient;

    @MockitoBean
    public PaymentClient paymentClient;

    @Autowired
    OrderRepository orderRepository;

    @Test
    void EmptyTest(){

    }


    @Test
    void testGetOrders() {
        webTestClient.get().uri("/orders")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testGetOrder() {
        long itemId = 1;
        long userId = 1;

        Payment payment = Payment.builder()
                .paymentId(UUID.randomUUID())
                .payer(UUID.randomUUID())
                .recipient(UUID.randomUUID())
                .amount(BigDecimal.TEN)
                .build();

        Mockito.when(paymentClient.createPayment(Mockito.any())).thenReturn(Mono.just(payment));

        webTestClient.post().uri(uriBuilder -> uriBuilder
                        .path("/cart/items/" + itemId)
                        .queryParam("action", CartAction.plus.toString())
                        .build())
                .exchange()
                .expectStatus().is3xxRedirection();

        webTestClient.post()
                .uri("/buy")
                .exchange()
                .expectStatus().is3xxRedirection();

        Mono<Long> orderId = orderRepository.findAllByUserId(userId)
                .elementAt(1)
                .map(Order::getId);

        webTestClient.get().uri("/orders/" + orderId)
                .exchange()
                .expectStatus();
    }
}