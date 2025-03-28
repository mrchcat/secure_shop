package com.github.mrchcat.intershop.item.controller;

import com.github.mrchcat.intershop.enums.Unit;
import com.github.mrchcat.intershop.item.dto.NewItemDto;
import com.github.mrchcat.intershop.item.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@ConditionalOnProperty(value = "application.items.load.enabled")
@Controller
@RequiredArgsConstructor
public class ItemAdminController {

    private final ItemService itemService;

    @GetMapping(value = "admin/items/download")
    public String downloadItem(Model model) {
        model.addAttribute("units", Unit.values());
        return "item-download";
    }

    @PostMapping(value = "admin/items/download", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String downloadItem(@ModelAttribute @Valid NewItemDto newItemDto) {
        itemService.downloadNewItem(newItemDto);
        return "redirect:/admin/items/download";
    }
}
