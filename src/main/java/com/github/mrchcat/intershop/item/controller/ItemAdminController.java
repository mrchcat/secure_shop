package com.github.mrchcat.intershop.item.controller;

import com.github.mrchcat.intershop.enums.Unit;
import com.github.mrchcat.intershop.item.dto.NewItemDto;
import com.github.mrchcat.intershop.item.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

@ConditionalOnProperty(value = "application.items.load.enabled")
@Controller
@RequiredArgsConstructor
public class ItemAdminController {

    private final ItemService itemService;

    @GetMapping(value = "admin/items/download")
    public Mono<Rendering> downloadItem() {
        return Mono.just(Rendering.view("item-download")
                .modelAttribute("units", Unit.values())
                .build());
    }

    @PostMapping(value = "admin/items/download", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<Rendering> downloadItem(@ModelAttribute("newItemDto") @Valid Mono<NewItemDto> newItemDto) {
        return itemService.downloadNewItem(newItemDto)
                .thenReturn(Rendering.view("redirect:/admin/items/download").build());
    }
}
