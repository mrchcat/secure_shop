package com.github.mrchcat.intershop.secure.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

@Controller
public class SecureController {

    @GetMapping("/shop-login")
    public Mono<Rendering> getLoginPage() {
        return Mono.just(Rendering.view("shop-login").build());
    }
}
