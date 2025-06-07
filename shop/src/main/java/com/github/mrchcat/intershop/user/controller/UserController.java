package com.github.mrchcat.intershop.user.controller;

import com.github.mrchcat.intershop.user.dto.UserRegisterDto;
import com.github.mrchcat.intershop.user.service.UserService;
import exceptions.IncorrectFormData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/user/registration")
    public Mono<Rendering> getAddNewPage(UserRegisterDto newUser,
                                         Authentication authentication) {
        var isAuth = Mono.just(authentication != null && authentication.isAuthenticated());
        return Mono.just(Rendering
                .view("user-registration")
                .modelAttribute("isAuth", isAuth)
                .build());
    }

    @PostMapping("/user/registration")
    public Mono<Rendering> addNewUser(@Valid UserRegisterDto newUser,
                                      BindingResult bindingResult,
                                      Authentication authentication) {
        var isAuth = Mono.just(authentication != null && authentication.isAuthenticated());
        if (bindingResult.hasErrors()) {
            return Mono.just(Rendering
                    .view("user-registration")
                    .modelAttribute("isAuth", isAuth)
                    .build());
        }
        return userService.addNewUser(newUser)
                .then(Mono.just(Rendering.view("/shop-login").build()))
                .doOnError(IncorrectFormData.class, e -> {
                    FieldError fieldError = new FieldError("userRegisterDto", e.getField(), e.getMessage());
                    bindingResult.addError(fieldError);
                })
                .onErrorReturn(IncorrectFormData.class, Rendering
                        .view("user-registration")
                        .modelAttribute("isAuth", isAuth)
                        .build());
    }

}
