package client;

import dto.Balance;
import dto.ErrorResponse;
import dto.Payment;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class PaymentClientImpl implements PaymentClient {
    private final WebClient webClient;

    public PaymentClientImpl(String serverUrl) {
        this.webClient = WebClient.create(serverUrl);
    }

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
