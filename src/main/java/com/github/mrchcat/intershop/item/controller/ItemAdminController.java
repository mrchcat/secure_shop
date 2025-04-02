package com.github.mrchcat.intershop.item.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;

@ConditionalOnProperty(value = "application.items.load.enabled")
@Controller
@RequiredArgsConstructor
public class ItemAdminController {

//    private final ItemService itemService;
//
//    @GetMapping(value = "admin/items/download")
//    public String downloadItem(Model model) {
//        model.addAttribute("units", Unit.values());
//        return "item-download";
//    }
//
//    @PostMapping(value = "admin/items/download", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public String downloadItem(@ModelAttribute @Valid NewItemDto newItemDto) {
//        itemService.downloadNewItem(newItemDto);
//        return "redirect:/admin/items/download";
//    }
}
