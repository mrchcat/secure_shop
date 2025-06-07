package com.github.mrchcat.intershop.user.service;

import com.github.mrchcat.intershop.user.domain.User;
import com.github.mrchcat.intershop.user.dto.UserRegisterDto;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<User> getUser(long userId);

    Mono<Void> addNewUser(UserRegisterDto newUser);
}
