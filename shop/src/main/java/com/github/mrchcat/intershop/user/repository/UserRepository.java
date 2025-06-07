package com.github.mrchcat.intershop.user.repository;

import com.github.mrchcat.intershop.user.domain.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserRepository extends ReactiveCrudRepository<User, Long> {

    Mono<Boolean> existsByEmail(String email);

    Mono<Boolean> existsByPaymentId(UUID PaymentId);

}
