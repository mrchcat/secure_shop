package com.github.mrchcat.intershop.user.mapper;

import com.github.mrchcat.intershop.user.domain.User;
import com.github.mrchcat.intershop.user.dto.UserRegisterDto;

public class UserMapper {
    public static User toUser(UserRegisterDto newUser) {
        return User.builder()
                .name(newUser.getName().trim())
                .email(newUser.getEmail().trim())
                .paymentId(newUser.getPaymentId())
                .build();
    }

}
