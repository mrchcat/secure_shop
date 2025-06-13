package com.github.mrchcat.client;

import com.github.mrchcat.dto.Balance;
import com.github.mrchcat.dto.ErrorResponse;
import com.github.mrchcat.dto.Payment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class PaymentClientImpl implements PaymentClient {
    private final WebClient webClient;
    private final ReactiveOAuth2AuthorizedClientManager manager;
    private final String GET_BALANCE_API_URI = "/api/v1/balance/";
    private final String CREATE_PAYMENT_API_URI = "/api/v1/payment/create";

    public PaymentClientImpl(String serverUrl, ReactiveOAuth2AuthorizedClientManager manager) {
        this.webClient = WebClient.create(serverUrl);
        this.manager = manager;
    }

    @Override
    public Mono<Balance> getBalance(UUID clientId, String oAuthClientId) {
        return getToken(oAuthClientId).flatMap(accessToken -> webClient.get()
                .uri(GET_BALANCE_API_URI + clientId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                        response.bodyToMono(ErrorResponse.class)
                                .flatMap(re -> Mono.error(new Exception(re.toString())))
                )
                .bodyToMono(Balance.class));
    }

    @Override
    public Mono<Payment> createPayment(Payment payment, String oAuthClientId) {
        return getToken(oAuthClientId).flatMap(accessToken -> webClient.post()
                .uri(CREATE_PAYMENT_API_URI)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .bodyValue(payment)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                        response.bodyToMono(ErrorResponse.class)
                                .flatMap(re -> Mono.error(new Exception(re.toString())))
                )
                .bodyToMono(Payment.class));
    }

    private Mono<String> getToken(String OAuthClientId) {
        return manager.authorize(OAuth2AuthorizeRequest
                        .withClientRegistrationId(OAuthClientId)
                        .principal("system")
                        .build())
                .map(OAuth2AuthorizedClient::getAccessToken)
                .map(OAuth2AccessToken::getTokenValue);

    }
}
