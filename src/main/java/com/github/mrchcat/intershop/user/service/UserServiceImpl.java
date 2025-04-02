package com.github.mrchcat.intershop.user.service;

import com.github.mrchcat.intershop.user.domain.User;
import com.github.mrchcat.intershop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public Mono<User> getUser(long userId) {
        return userRepository
                .findById(userId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("User not found")));
    }
}
