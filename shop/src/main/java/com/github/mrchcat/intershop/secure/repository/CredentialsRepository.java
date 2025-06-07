package com.github.mrchcat.intershop.secure.repository;

import com.github.mrchcat.intershop.secure.domain.Credentials;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

public interface CredentialsRepository extends ReactiveCrudRepository<Credentials, Long> {

    Mono<UserDetails> findByUsername(String username);

    Mono<Boolean> existsByUsername(String username);

    Mono<Boolean> existsByPassword(String password);

    @Override
    <S extends Credentials> Mono<S> save(S entity);
}
