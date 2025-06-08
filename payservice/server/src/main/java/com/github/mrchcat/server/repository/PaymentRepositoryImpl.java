package com.github.mrchcat.server.repository;


import com.github.mrchcat.dto.Balance;
import com.github.mrchcat.dto.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {

    private final DatabaseClient databaseClient;

    @Override
    public Mono<Balance> getBalance(UUID clientId) {
        String selectQuery = """
                SELECT cl.client_uuid AS client_uuid, a.account, a.balance,cr.currency
                FROM clients AS cl
                JOIN accounts AS a ON cl.id=a.client_id
                JOIN currencies AS cr ON cr.id=a.currency_id
                WHERE cl.client_uuid=:clientId
                """;
        return databaseClient.sql(selectQuery)
                .bind("clientId", clientId)
                .map((row, metadata) -> new Balance(
                        row.get("client_uuid", UUID.class),
                        row.get("account", Long.class),
                        row.get("balance", BigDecimal.class),
                        row.get("currency", String.class)))
                .one();
    }

    @Override
    public Mono<Boolean> hasClients(Payment payment) {
        String selectQuery = """
                SELECT id
                FROM clients
                WHERE client_uuid=:payer OR client_uuid=:recipient
                """;
        return databaseClient.sql(selectQuery)
                .bind("payer", payment.getPayer())
                .bind("recipient", payment.getRecipient())
                .fetch()
                .rowsUpdated()
                .map(numOfRows -> numOfRows.equals(2L));
    }

    @Override
    public Mono<Boolean> hasClient(UUID clientId) {
        String selectQuery = """
                SELECT id
                FROM clients
                WHERE client_uuid=:clientId
                """;
        return databaseClient.sql(selectQuery)
                .bind("clientId", clientId)
                .fetch()
                .rowsUpdated()
                .map(numOfRows -> numOfRows.equals(1L));
    }

    @Override
    public Mono<Payment> transfer(Payment payment) {
        String changeAmountQuery = """
                UPDATE accounts
                SET balance=balance+:amount
                WHERE client_id =(
                	SELECT id
                	FROM clients
                	WHERE client_uuid=:client)
                """;

        Mono<Long> debitMono = databaseClient.sql(changeAmountQuery)
                .bind("amount", payment.getAmount().negate())
                .bind("client", payment.getPayer())
                .fetch()
                .rowsUpdated()
                .flatMap(updatedQuantity -> {
                    if (updatedQuantity != 1) {
                        return Mono.error(new IncorrectResultSizeDataAccessException(updatedQuantity.intValue()));
                    }
                    return Mono.empty();
                });
        Mono<Void> addMono = databaseClient.sql(changeAmountQuery)
                .bind("amount", payment.getAmount())
                .bind("client", payment.getRecipient())
                .fetch()
                .rowsUpdated()
                .flatMap(updatedQuantity -> {
                    if (updatedQuantity != 1) {
                        return Mono.error(new IncorrectResultSizeDataAccessException(updatedQuantity.intValue()));
                    }
                    return Mono.empty();
                });

        return addMono.then(debitMono)
                .then(Mono.just(payment));
    }
}
