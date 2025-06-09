package com.github.mrchcat.client;

import com.github.mrchcat.dto.Payment;
import com.nimbusds.oauth2.sdk.token.AccessToken;
import com.nimbusds.oauth2.sdk.token.BearerAccessToken;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

@ExtendWith(MockitoExtension.class)
class PaymentClientImplTest {

    @Mock
    ReactiveOAuth2AuthorizedClientManager manager;

    final static MockWebServer server = new MockWebServer();
    PaymentClient paymentClient;

    String oAuthClientId="some token";

    @BeforeAll
    static void setUp() throws IOException {
        server.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        server.shutdown();
    }

    @BeforeEach
    void initialize() {
        paymentClient = new PaymentClientImpl(server.url("/").toString(),manager);
        OAuth2AccessToken token=new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER,
                oAuthClientId,
                Instant.now(),
                Instant.now().plus(10, ChronoUnit.SECONDS));
        ClientRegistration  clientRegistration= ClientRegistration.withRegistrationId("11")
                .authorizationGrantType(AuthorizationGrantType.JWT_BEARER)
                .build();
        OAuth2AuthorizedClient client=new OAuth2AuthorizedClient(clientRegistration,"sss",token);
        Mockito.when(manager.authorize(Mockito.any())).thenReturn(Mono.just(client));
    }


    @Test
    void getBalanceTest() throws InterruptedException {
        UUID clientId = UUID.randomUUID();
        server.enqueue(new MockResponse().setResponseCode(404));
        paymentClient.getBalance(clientId,oAuthClientId).block();
        RecordedRequest recordedRequest = server.takeRequest();
        assertEquals("GET", recordedRequest.getMethod());
        assertEquals("/api/v1/balance/" + clientId, recordedRequest.getPath());
    }

    @Test
    void getPaymentTest() throws InterruptedException {
        UUID payerId = UUID.fromString("db1673e9-bf97-4dfa-acdb-88a280d4d144");
        UUID recipientId = UUID.fromString("623ff0e5-2069-4051-88dc-fea52a85ffab");
        String oAuthClientId="some token";
        BigDecimal amount = BigDecimal.valueOf(100, 2);
        Payment payment = Payment.builder()
                .paymentId(UUID.randomUUID())
                .payer(payerId)
                .recipient(recipientId)
                .amount(amount)
                .build();
        server.enqueue(new MockResponse().setResponseCode(404));
        paymentClient.createPayment(payment,oAuthClientId).block();
        RecordedRequest recordedRequest = server.takeRequest();
        assertEquals("POST", recordedRequest.getMethod());
    }

}