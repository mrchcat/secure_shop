package client;


import dto.Balance;
import dto.Payment;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface PaymentClient {
    Mono<Payment> createPayment(Payment payment);

    Mono<Balance> getBalance(UUID clientID);
}
