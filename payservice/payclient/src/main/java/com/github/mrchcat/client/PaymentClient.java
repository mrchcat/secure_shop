package com.github.mrchcat.client;


import com.github.mrchcat.dto.Balance;
import com.github.mrchcat.dto.Payment;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface PaymentClient {
    Mono<Payment> createPayment(Payment payment);

    Mono<Balance> getBalance(UUID clientID);
}
