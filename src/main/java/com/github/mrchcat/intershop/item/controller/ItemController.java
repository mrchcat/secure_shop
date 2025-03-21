package com.github.mrchcat.intershop.item.controller;

import com.github.mrchcat.intershop.enums.BasketAction;
import com.github.mrchcat.intershop.enums.SortOrder;
import com.github.mrchcat.intershop.item.domain.Item;
import com.github.mrchcat.intershop.item.dto.ItemDto;
import com.github.mrchcat.intershop.item.dto.MainItemsDto;
import com.github.mrchcat.intershop.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final long userId = 1;

    @GetMapping("/")
    public String redirectToAllItems(Model model) {
        return "redirect:main/items";
    }

    @GetMapping("/items/{id}")
    public String getItem(Model model,
                          @PathVariable("id") long itemId) {
        ItemDto item = itemService.getItem(userId, itemId);
        model.addAttribute("item", item);
        return "item";
    }

    @GetMapping("/main/items")
    public String getAllItems(Model model,
                              @RequestParam(name = "search", defaultValue = "") String search,
                              @RequestParam(name = "sort", defaultValue = "NO") SortOrder sort,
                              @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                              @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort.sort);
        MainItemsDto dto = itemService.getItems(userId, search, pageable);
        model.addAttribute("paging", dto.getPage());
        model.addAttribute("items", dto.getItems());
        model.addAttribute("sort", sort.toString());
        return "main";
    }

    @PostMapping("/main/items/{id}")
    void changeBasket(Model model,
                      @PathVariable("id") long itemId,
                      @RequestParam("action") BasketAction action) {

    }

}
