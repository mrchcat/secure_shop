package com.github.mrchcat.intershop.item.controller;

import com.github.mrchcat.dto.Balance;
import com.github.mrchcat.dto.ErrorResponse;
import com.github.mrchcat.intershop.enums.SortOrder;
import com.github.mrchcat.intershop.item.dto.ActionDto;
import com.github.mrchcat.intershop.item.dto.SearchAndPageDto;
import com.github.mrchcat.intershop.item.service.ItemService;
import com.github.mrchcat.intershop.secure.domain.Credentials;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ItemPublicController {

    private final ItemService itemService;

    @GetMapping("/")
    public Mono<Rendering> redirectToAllItems() {
        return Mono.just(Rendering.view("redirect:main/items").build());
    }

    @GetMapping("/items/{id}")
    public Mono<Rendering> getItem(@PathVariable("id") long itemId,
                                   Authentication authentication) {
        var itemDto = itemService.getItemDto(getUserId(authentication), itemId);
        var isAuth = Mono.just(authentication != null && authentication.isAuthenticated());
        return Mono.just(Rendering
                .view("item")
                .modelAttribute("item", itemDto)
                .modelAttribute("isAuth", isAuth)
                .build());
    }

    @GetMapping("/main/items")
    public Mono<Rendering> getAllItems(@ModelAttribute("searchAndPageDto") SearchAndPageDto searchAndPageDto,
                                       Authentication authentication) {
        SortOrder sortOrder = searchAndPageDto.getSort();
        Pageable pageable = PageRequest.of(
                searchAndPageDto.getPageNumber(),
                searchAndPageDto.getPageSize(),
                sortOrder.sort);
        String search = searchAndPageDto.getSearch();

        var itemPage = itemService.getItemDtos(getUserId(authentication), pageable, search);
        var isAuth = Mono.just(authentication != null && authentication.isAuthenticated());
        return Mono.just(Rendering
                .view("main")
                .modelAttribute("sort", sortOrder.toString())
                .modelAttribute("itemPage", itemPage)
                .modelAttribute("isAuth", isAuth)
                .build());
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("main/items/{id}")
    public Mono<Rendering> updateCartInMain(@PathVariable("id") long itemId,
                                            @ModelAttribute("actionDto") ActionDto actionDto,
                                            Authentication authentication) {
        long userId = getUserId(authentication).orElseThrow(() -> new NoSuchElementException("userId не найден"));
        return itemService
                .changeCart(userId, itemId, actionDto.getAction())
                .thenReturn(Rendering.view("redirect:/main/items").build());
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/items/{id}")
    public Mono<Rendering> updateCartInItem(@PathVariable("id") long itemId,
                                            @ModelAttribute("actionDto") ActionDto actionDto,
                                            Authentication authentication) {

        long userId = getUserId(authentication).orElseThrow(() -> new NoSuchElementException("userId не найден"));
        return itemService
                .changeCart(userId, itemId, actionDto.getAction())
                .thenReturn(Rendering
                        .view("redirect:/items/" + itemId)
                        .build());
    }

    private Optional<Long> getUserId(Authentication authentication) {
        Optional<Long> userId = Optional.empty();
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof Credentials credentials) {
                userId = Optional.of(credentials.getUserId());
            }
        }
        return userId;
    }

    private final ReactiveOAuth2AuthorizedClientManager manager;
    private final String GET_BALANCE_API_URI = "/api/v1/balance/";

    @GetMapping("/test")
    public Mono<Balance> test() {
        WebClient webClient = WebClient.create("http://127.0.0.1:8081");
        System.out.println("проверка баланса");
        return getToken("shop").flatMap(accessToken -> {
            System.out.println("Токен=" + accessToken);
            return webClient.get()
                    .uri(GET_BALANCE_API_URI + "db1673e9-bf97-4dfa-acdb-88a280d4d144")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, response ->
                            response.bodyToMono(ErrorResponse.class)
                                    .flatMap(re -> Mono.error(new Exception(re.toString())))
                    )
                    .bodyToMono(Balance.class);
        });
    }

    private Mono<String> getToken(String OAuthClientId) {
        return manager.authorize(OAuth2AuthorizeRequest
                        .withClientRegistrationId(OAuthClientId)
                        .principal("system")
                        .build())
                .map(OAuth2AuthorizedClient::getAccessToken)
                .map(OAuth2AccessToken::getTokenValue);

    }

}
