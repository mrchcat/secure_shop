package com.github.mrchcat.intershop.user.repository;

import com.github.mrchcat.intershop.user.domain.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UserRepository extends ReactiveCrudRepository<User, Long> {
}
