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
    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public Mono<Payment> createPayment(Payment payment) {
        return paymentRepository.hasClients(payment).flatMap(hasClients -> {
            if (!hasClients) {
                return Mono.error(new ClientNotFoundException(payment.getPayer() + ";" + payment.getRecipient()));
            }
            return paymentRepository.getBalance(payment.getPayer()).flatMap(balance -> {
                if (balance.getAmount().compareTo(payment.getAmount()) < 0) {
                    return Mono.error(new BalanceNotEnough(payment.getPaymentId().toString()));
                }
                return paymentRepository.transfer(payment);
            });
        });
    }

    @Override
    public Mono<Balance> getBalance(UUID clientId) {
        return paymentRepository.hasClient(clientId).flatMap(hasClient -> {
            if (hasClient) {
                return paymentRepository.getBalance(clientId).switchIfEmpty(Mono.error(new InternalError()));
            }
            return Mono.error(new ClientNotFoundException(String.valueOf(clientId)));
        });
    }
}
