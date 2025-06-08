package com.github.mrchcat.server.controller;

import com.github.mrchcat.dto.Balance;
import com.github.mrchcat.dto.Payment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class PaymentControllerTest {
//
//    @Autowired
//    private WebTestClient webTestClient;
//
//    @Test
//    void getCorrectBalanceTest() {
//        UUID clientId = UUID.fromString("db1673e9-bf97-4dfa-acdb-88a280d4d144");
//        webTestClient.get()
//                .uri("/api/v1/balance/" + clientId)
//                .exchange()
//                .expectStatus().isOk()
//                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
//                .expectBody(Balance.class)
//                .consumeWith(response -> {
//                    Balance balance = response.getResponseBody();
//                    Assertions.assertNotNull(balance);
//                    Assertions.assertEquals(balance.getClient(), clientId);
//                    Assertions.assertEquals(BigDecimal.valueOf(1000000, 2), balance.getAmount());
//                });
//
//    }
//
//    @Test
//    void getBalanceNonExistingClientTest() {
//        UUID clientId = UUID.fromString("5967cc77-2bec-4d0b-b014-be357a39c9a1");
//        webTestClient.get()
//                .uri("/api/v1/balance/" + clientId)
//                .exchange()
//                .expectStatus().isNotFound();
//    }
//
//    @Test
//    void getBalanceBadClientIdTest() {
//        String clientId = "bla-bla";
//        webTestClient.get()
//                .uri("/api/v1/balance/" + clientId)
//                .exchange()
//                .expectStatus().isEqualTo(HttpStatusCode.valueOf(400));
//    }
//
//    @Test
//    void correctPaymentTest() {
//        UUID payerId = UUID.fromString("db1673e9-bf97-4dfa-acdb-88a280d4d144");
//        UUID recipientId = UUID.fromString("623ff0e5-2069-4051-88dc-fea52a85ffab");
//        BigDecimal amount = BigDecimal.valueOf(100, 2);
//        Payment payment = Payment.builder()
//                .paymentId(UUID.randomUUID())
//                .payer(payerId)
//                .recipient(recipientId)
//                .amount(amount)
//                .build();
//
//        BigDecimal payerAmountBefore = webTestClient.get()
//                .uri("/api/v1/balance/" + payerId)
//                .exchange()
//                .returnResult(Balance.class)
//                .getResponseBody()
//                .blockFirst()
//                .getAmount();
//        BigDecimal recipientAmountBefore = webTestClient.get()
//                .uri("/api/v1/balance/" + recipientId)
//                .exchange()
//                .returnResult(Balance.class)
//                .getResponseBody()
//                .blockFirst()
//                .getAmount();
//
//        webTestClient.post()
//                .uri("/api/v1/payment/create")
//                .bodyValue(payment)
//                .exchange()
//                .expectStatus().isOk()
//                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
//                .expectBody(Payment.class)
//                .consumeWith(response -> {
//                    Payment returnedPayment = response.getResponseBody();
//                    Assertions.assertNotNull(returnedPayment);
//                    Assertions.assertEquals(payment, returnedPayment);
//                });
//
//        BigDecimal payerAmountAfter = webTestClient.get()
//                .uri("/api/v1/balance/" + payerId)
//                .exchange()
//                .returnResult(Balance.class)
//                .getResponseBody()
//                .blockFirst()
//                .getAmount();
//        BigDecimal recipientAmountAfter = webTestClient.get()
//                .uri("/api/v1/balance/" + recipientId)
//                .exchange()
//                .returnResult(Balance.class)
//                .getResponseBody()
//                .blockFirst()
//                .getAmount();
//
//        BigDecimal recipientDiff = recipientAmountAfter.subtract(recipientAmountBefore);
//        Assertions.assertEquals(amount, recipientDiff);
//
//        BigDecimal payerDiff = payerAmountAfter.subtract(payerAmountBefore);
//        Assertions.assertEquals(amount.negate(), payerDiff);
//    }
//
//    @Test
//    void paymentTooBigAmountPaymentTest() {
//        UUID payerId = UUID.fromString("db1673e9-bf97-4dfa-acdb-88a280d4d144");
//        UUID recipientId = UUID.fromString("623ff0e5-2069-4051-88dc-fea52a85ffab");
//        BigDecimal amount = BigDecimal.valueOf(Long.MAX_VALUE, 2);
//        Payment payment = Payment.builder()
//                .paymentId(UUID.randomUUID())
//                .payer(payerId)
//                .recipient(recipientId)
//                .amount(amount)
//                .build();
//        webTestClient.post()
//                .uri("/api/v1/payment/create")
//                .bodyValue(payment)
//                .exchange()
//                .expectStatus().isEqualTo(HttpStatusCode.valueOf(422));
//    }
//
//    @Test
//    void paymentNotExistingClientIdTest() {
//        UUID payerId = UUID.fromString("d8176db9-b437-44e3-9933-236e69f4d703");
//        UUID recipientId = UUID.fromString("623ff0e5-2069-4051-88dc-fea52a85ffab");
//        BigDecimal amount = BigDecimal.valueOf(Long.MAX_VALUE, 2);
//        Payment payment = Payment.builder()
//                .paymentId(UUID.randomUUID())
//                .payer(payerId)
//                .recipient(recipientId)
//                .amount(amount)
//                .build();
//        webTestClient.post()
//                .uri("/api/v1/payment/create")
//                .bodyValue(payment)
//                .exchange()
//                .expectStatus().isEqualTo(HttpStatusCode.valueOf(404));
//    }
//
//    @Test
//    void paymentNegativeAmountId() {
//        UUID payerId = UUID.fromString("db1673e9-bf97-4dfa-acdb-88a280d4d144");
//        UUID recipientId = UUID.fromString("623ff0e5-2069-4051-88dc-fea52a85ffab");
//        BigDecimal amount = BigDecimal.ONE.negate();
//        Payment payment = Payment.builder()
//                .paymentId(UUID.randomUUID())
//                .payer(payerId)
//                .recipient(recipientId)
//                .amount(amount)
//                .build();
//        webTestClient.post()
//                .uri("/api/v1/payment/create")
//                .bodyValue(payment)
//                .exchange()
//                .expectStatus().isEqualTo(HttpStatusCode.valueOf(400));
//    }

}