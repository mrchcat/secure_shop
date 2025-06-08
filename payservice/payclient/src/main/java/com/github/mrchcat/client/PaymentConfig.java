package com.github.mrchcat.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;

@Configuration
public class PaymentConfig {

    @Value("${payment.server.url}")
    private String serverUrl;

    @Bean
    PaymentClient getPaymentClient(ReactiveOAuth2AuthorizedClientManager manager) {
        return new PaymentClientImpl(serverUrl, manager);
    }
}
