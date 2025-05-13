package com.github.mrchcat.server.service;


import com.github.mrchcat.dto.Balance;
import com.github.mrchcat.dto.Payment;
import com.github.mrchcat.server.exception.BalanceNotEnough;
import com.github.mrchcat.server.exception.ClientNotFoundException;
import com.github.mrchcat.server.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository payRep;

    @Override
    @Transactional
    public Mono<Payment> createPayment(Payment payment) {
        return payRep.hasClients(payment).flatMap(hasClients -> {
            if (!hasClients) {
                return Mono.error(new ClientNotFoundException(payment.getPayer() + ";" + payment.getRecipient()));
            }
            return payRep.getBalance(payment.getPayer()).flatMap(balance -> {
                if (balance.getAmount().compareTo(payment.getAmount()) < 0) {
                    return Mono.error(new BalanceNotEnough(payment.getPaymentId().toString()));
                }
                return payRep.transfer(payment);
            });
        });
    }

    @Override
    public Mono<Balance> getBalance(UUID clientId) {
        return payRep.getBalance(clientId)
                .switchIfEmpty(Mono.error(new ClientNotFoundException(clientId.toString())));
    }

}
