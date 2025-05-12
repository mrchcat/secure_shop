package com.github.mrchcat.server.repository;


import com.github.mrchcat.dto.Balance;
import com.github.mrchcat.dto.Payment;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface PaymentRepository {

    Mono<Balance> getBalance(UUID clientId);

    Mono<Boolean> hasClients(Payment payment);

    Mono<Payment> transfer(Payment payment);

}
