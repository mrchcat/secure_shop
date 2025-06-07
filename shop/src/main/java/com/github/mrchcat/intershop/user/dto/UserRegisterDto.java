package com.github.mrchcat.intershop.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Getter
@Setter
public class UserRegisterDto {
    @NotNull
    @NotBlank
    @Length(min = 5, max = 256)
    private String name;

    @NotNull
    @Length(max = 256)
    @Email
    private String email;

    @NotNull
    private UUID paymentId;

    @NotNull
    @NotBlank
    @Length(min = 5, max = 256)
    private String login;

    @NotNull
    @NotBlank
    @Length(min = 5, max = 256)
    private String password;
}
