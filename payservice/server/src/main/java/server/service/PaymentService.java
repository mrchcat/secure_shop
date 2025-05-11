package server.service;


import dto.Balance;
import dto.Payment;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface PaymentService {

    Mono<Payment> createPayment(Payment payment);

    Mono<Balance> getBalance(UUID clientID);

}
