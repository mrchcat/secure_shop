package com.github.mrchcat.intershop.item.controller;

import com.github.mrchcat.intershop.enums.SortOrder;
import com.github.mrchcat.intershop.item.dto.ActionDto;
import com.github.mrchcat.intershop.item.dto.ItemDto;
import com.github.mrchcat.intershop.item.dto.SearchAndPageDto;
import com.github.mrchcat.intershop.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ItemPublicController {

    private final ItemService itemService;

    @Value("${application.user_id}")
    private long userId;

    @GetMapping("/")
    public String redirectToAllItems(Model model) {
        return "redirect:main/items";
    }

    @GetMapping("/items/{id}")
    public Mono<Rendering> getItem(@PathVariable("id") long itemId) {
        var itemDto = itemService.getItem(userId, itemId);
        return Mono.just(Rendering
                .view("item")
                .modelAttribute("item", itemDto)
                .build());
    }


    @PostMapping("/items/{id}")
    public Mono<Rendering> updateCartInItem(@PathVariable("id") long itemId,
                                            @ModelAttribute("actionDto") ActionDto actionDto) {
        return itemService.changeCart(userId, itemId, actionDto.getAction())
                .thenReturn(Rendering
                        .view("redirect:/items/" + itemId)
                        .build());
    }

    @GetMapping("/main/items")
    public Mono<Rendering> getAllItems(@ModelAttribute("searchAndPageDto") SearchAndPageDto searchAndPageDto) {
        SortOrder sortOrder = searchAndPageDto.getSort();
        Pageable pageable = PageRequest.of(
                searchAndPageDto.getPageNumber(),
                searchAndPageDto.getPageSize(),
                sortOrder.sort);
        String search = searchAndPageDto.getSearch();

        Mono<Page<List<ItemDto>>> itemPage = itemService.getItems(userId, pageable, search);
        return Mono.just(Rendering
                .view("main")
                .modelAttribute("sort", sortOrder.toString())
                .modelAttribute("itemPage", itemPage)
                .build());
    }

    @PostMapping("main/items/{id}")
    public Mono<Rendering> updateCartInMain(@PathVariable("id") long itemId,
                                            @ModelAttribute("actionDto") ActionDto actionDto) {
        return itemService
                .changeCart(userId, itemId, actionDto.getAction())
                .thenReturn(Rendering.view("redirect:/main/items").build());
    }
}
