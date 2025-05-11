package com.github.mrchcat.intershop.user.service;

import com.github.mrchcat.intershop.user.domain.User;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<User> getUser(long userId);
}
