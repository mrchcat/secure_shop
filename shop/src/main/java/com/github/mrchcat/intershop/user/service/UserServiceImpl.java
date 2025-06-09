package com.github.mrchcat.intershop.user.service;

import com.github.mrchcat.intershop.cart.service.AddCartService;
import com.github.mrchcat.intershop.secure.mapper.CredentialsMapper;
import com.github.mrchcat.intershop.secure.service.CredentialsService;
import com.github.mrchcat.intershop.user.domain.User;
import com.github.mrchcat.intershop.user.dto.UserRegisterDto;
import com.github.mrchcat.intershop.user.mapper.UserMapper;
import com.github.mrchcat.intershop.user.repository.UserRepository;
import com.github.mrchcat.intershop.exceptions.IncorrectFormData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CredentialsService credentialsService;
    private final CredentialsMapper credentialsMapper;
    private final AddCartService addCartService;

    @Override
    public Mono<User> getUser(long userId) {
        return userRepository
                .findById(userId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("User not found")));
    }

    @Override
    public Mono<Void> addNewUser(UserRegisterDto newUser) {
        String email = newUser.getEmail();
        Mono<Void> checkEmail = userRepository.existsByEmail(email).flatMap(r -> {
                    if (r) {
                        var existingEmailException = new IncorrectFormData("email=" + email + " уже существует");
                        existingEmailException.setField("email");
                        return Mono.error(existingEmailException);
                    }
                    return Mono.empty();
                }
        );

        UUID paymentId = newUser.getPaymentId();
        Mono<Void> checkPaymentId = userRepository.existsByPaymentId(paymentId).flatMap(r -> {
            if (r) {
                var existingPaymentIdException = new IncorrectFormData("PaymentId=" + paymentId + " уже существует");
                existingPaymentIdException.setField("paymentId");
                return Mono.error(existingPaymentIdException);
            }
            return Mono.empty();
        });
        String login = newUser.getLogin();
        Mono<Void> checkUsername = credentialsService.existsByUsername(login).flatMap(r -> {
                    if (r) {
                        var existingUserNameException = new IncorrectFormData("Login=" + login + " уже существует");
                        existingUserNameException.setField("login");
                        return Mono.error(existingUserNameException);
                    }
                    return Mono.empty();
                }
        );
        return checkEmail
                .thenEmpty(checkPaymentId)
                .thenEmpty(checkUsername)
                .then(userRepository.save(UserMapper.toUser(newUser))
                        .flatMap(user -> {
                            var newCredentials = credentialsMapper.toCommonUserCredentials(newUser, user.getId());
                            return credentialsService.save(newCredentials);
                        }))
                .flatMap(cr -> addCartService.createCartForUser(cr.getUserId()))
                .then();
    }
}
