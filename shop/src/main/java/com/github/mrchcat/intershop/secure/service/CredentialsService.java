package com.github.mrchcat.intershop.secure.service;


import com.github.mrchcat.intershop.secure.domain.Credentials;
import com.github.mrchcat.intershop.secure.repository.CredentialsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsPasswordService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Transactional
public class CredentialsService implements ReactiveUserDetailsService, ReactiveUserDetailsPasswordService {
    private final CredentialsRepository credentialsRepository;


    @Override
    public Mono<UserDetails> updatePassword(UserDetails user, String newPassword) {
        return null;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return credentialsRepository.findByUsername(username);
    }

    public Mono<Boolean> existsByUsername(String username) {
        return credentialsRepository.existsByUsername(username);
    }

    public Mono<Boolean> existsByPassword(String password) {
        return credentialsRepository.existsByPassword(password);
    }

    public Mono<Credentials> save(Credentials credentials) {
        return credentialsRepository.save(credentials);
    }

}
