package com.github.mrchcat.server.service;



import com.github.mrchcat.dto.Balance;
import com.github.mrchcat.dto.Payment;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface PaymentService {

    Mono<Payment> createPayment(Payment payment);

    Mono<Balance> getBalance(UUID clientID);

}
