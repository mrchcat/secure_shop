package com.github.mrchcat.intershop.user.repository;

import com.github.mrchcat.intershop.user.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
