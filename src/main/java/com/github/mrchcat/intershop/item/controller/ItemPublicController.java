package com.github.mrchcat.intershop.item.controller;

import com.github.mrchcat.intershop.item.domain.Item;
import com.github.mrchcat.intershop.item.dto.ItemDto;
import com.github.mrchcat.intershop.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
public class ItemPublicController {

    private final ItemService itemService;

    @Value("${application.user_id}")
    private long userId;

    @GetMapping("/")
    public String redirectToAllItems(Model model) {
        return "redirect:main/items";
    }

    @GetMapping("/items/{id}")
    public Mono<Rendering> getItem(@ModelAttribute("item") Item idem) {
        Mono<ItemDto> item = itemService.getItem(userId, idem.getId());
        return Mono.just(Rendering
                .view("item")
                .modelAttribute("item", item)
                .modelAttribute("count", item.flatMap(ItemDto::getCount))
                .modelAttribute("inCart", item.flatMap(ItemDto::getInCart))
                .build());
    }
//
//    @PostMapping("/items/{id}")
//    public String updateCartInItem(Model model,
//                                   @ModelAttribute("item") Item item,
//                                   @RequestParam("action") CartAction action) {
//        itemService.changeCart(userId, item.getId(), action);
//        return "redirect:/items/" + item.getId();
//    }
//
//    @GetMapping("/main/items")
//    public String getAllItems(Model model,
//                              @RequestParam(name = "search", defaultValue = "") String search,
//                              @RequestParam(name = "sort", defaultValue = "NO") SortOrder sort,
//                              @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
//                              @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber) {
//
//        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort.sort);
//        MainItemsDto dto = itemService.getItems(userId, search, pageable);
//        model.addAttribute("paging", dto.getPage());
//        model.addAttribute("items", dto.getItems());
//        model.addAttribute("sort", sort.toString());
//        return "main";
//    }
//
//    @PostMapping("main/items/{id}")
//    public String updateCartInMain(Model model,
//                                   @PathVariable("id") long itemId,
//                                   @RequestParam("action") CartAction action) {
//        itemService.changeCart(userId, itemId, action);
//        return "redirect:/main/items";
//    }
}
