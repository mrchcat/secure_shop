package com.github.mrchcat.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentConfig {

    @Value("${payment.server.url}")
    private String serverUrl;

    @Bean
    PaymentClient getPaymentClient() {
        return new PaymentClientImpl(serverUrl);
    }
}
