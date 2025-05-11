package server.controller;

import dto.Balance;
import dto.Payment;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import server.service.PaymentService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Validated
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/payment/create")
    Mono<ResponseEntity<Payment>> createPayment(@Valid @RequestBody Payment payment) {
        return paymentService.createPayment(payment)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/balance/{clientId}")
    Mono<ResponseEntity<Balance>> getBalance(@PathVariable @NotNull UUID clientId) {
        return paymentService.getBalance(clientId)
                .map(ResponseEntity::ok);
    }


}
