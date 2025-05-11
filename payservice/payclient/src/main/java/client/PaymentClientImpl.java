package client;


import dto.Balance;
import dto.ErrorResponse;
import dto.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class PaymentClientImpl implements PaymentClient {
    private final WebClient webClient = WebClient.create("http://127.0.0.1:8081");

    @Override
    public Mono<Payment> createPayment(Payment payment) {
        return webClient.post()
                .uri("/payment/create")
                .bodyValue(payment)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                        response.bodyToMono(ErrorResponse.class)
                                .flatMap(re -> Mono.error(new Exception(re.toString())))
                )
                .bodyToMono(Payment.class);
    }

    @Override
    public Mono<Balance> getBalance(UUID clientId) {
        return webClient.get()
                .uri("balance/" + clientId)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                        response.bodyToMono(ErrorResponse.class)
                                .flatMap(re -> Mono.error(new Exception(re.toString())))
                )
                .bodyToMono(Balance.class);
    }
}
