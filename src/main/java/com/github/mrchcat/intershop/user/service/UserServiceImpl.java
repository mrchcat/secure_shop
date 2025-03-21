package com.github.mrchcat.intershop.user.service;

import com.github.mrchcat.intershop.user.domain.User;
import com.github.mrchcat.intershop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User getUser(long userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
