package com.github.mrchcat.server.controller;

import com.github.mrchcat.dto.Balance;
import com.github.mrchcat.dto.Payment;
import com.github.mrchcat.server.service.PaymentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Validated
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/api/v1/payment/create")
    Mono<Payment> createPayment(@Valid @RequestBody Payment payment) {
        return paymentService.createPayment(payment);
    }

    @GetMapping("/api/v1/balance/{clientId}")
    Mono<Balance> getBalance(@PathVariable @NotNull UUID clientId) {
        return paymentService.getBalance(clientId);
    }


}
