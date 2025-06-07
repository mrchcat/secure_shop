package com.github.mrchcat.intershop.secure.mapper;


import com.github.mrchcat.intershop.enums.Role;
import com.github.mrchcat.intershop.secure.domain.Credentials;
import com.github.mrchcat.intershop.user.dto.UserRegisterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CredentialsMapper {
    private final PasswordEncoder encoder;

    public Credentials toCommonUserCredentials(UserRegisterDto addUserDto, long userId) {
        return Credentials.builder()
                .username(addUserDto.getLogin().trim())
                .password(encoder.encode(addUserDto.getPassword().trim()))
                .roles("ROLE_" + Role.USER.roleName)
                .enabled(true)
                .userId(userId)
                .created(LocalDateTime.now())
                .build();
    }
}
