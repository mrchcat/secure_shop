package server.service;


import dto.Balance;
import dto.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import server.exception.BalanceNotEnough;
import server.exception.ClientNotFoundException;
import server.repository.PaymentRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository payRep;

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Mono<Payment> createPayment(Payment p) {
        return payRep.hasClients(p).flatMap(hasClients -> {
            if (!hasClients) {
                return Mono.error(new ClientNotFoundException(p.getPayer()+";"+p.getRecipient()));
            }
            return payRep.getBalance(p.getPayer()).flatMap(balance -> {
                if (balance.getAmount().compareTo(p.getAmount()) < 0) {
                    return Mono.error(new BalanceNotEnough(p.getPaymentId().toString()));
                }
                return payRep.transfer(p);
            });
        });
    }

    @Override
    public Mono<Balance> getBalance(UUID clientId) {
        return payRep.getBalance(clientId)
                .switchIfEmpty(Mono.error(new ClientNotFoundException(clientId.toString())));
    }

}
