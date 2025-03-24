package com.github.mrchcat.intershop.cart.controller;

import com.github.mrchcat.intershop.cart.dto.CartItemsDto;
import com.github.mrchcat.intershop.cart.service.CartService;
import com.github.mrchcat.intershop.enums.CartAction;
import com.github.mrchcat.intershop.item.dto.ItemDto;
import com.github.mrchcat.intershop.item.dto.MainItemsDto;
import com.github.mrchcat.intershop.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final ItemService itemService;
    private final long userId = 1;

    @GetMapping("/cart/items")
    public String getCartItems(Model model) {
        CartItemsDto cartItemsDto = cartService.getCartItems(userId);
        model.addAttribute("items", cartItemsDto.getItemDtoList());
        model.addAttribute("total", cartItemsDto.getTotal());
        model.addAttribute("empty", cartItemsDto.isCartEmpty());
        return "cart";
    }

    @PostMapping("/cart/items/{id}")
    public String updateCartInMain(Model model,
                                   @PathVariable("id") long itemId,
                                   @RequestParam("action") CartAction action) {
        itemService.changeCart(userId, itemId, action);
        return "redirect:/cart/items";
    }

}
