package com.github.mrchcat.intershop.cart.controller;

import com.github.mrchcat.intershop.cart.service.CartService;
import com.github.mrchcat.intershop.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final ItemService itemService;

    @Value("${application.user_id}")
    private long userId;

//    @GetMapping("/cart/items")
//    public String getCartItems(Model model) {
//        CartItemsDto cartItemsDto = cartService.getCartItems(userId);
//        model.addAttribute("items", cartItemsDto.getItemDtoList());
//        model.addAttribute("total", cartItemsDto.getTotal());
//        model.addAttribute("empty", cartItemsDto.isCartEmpty());
//        return "cart";
//    }
//
//    @PostMapping("/cart/items/{id}")
//    public String updateCartInMain(Model model,
//                                   @PathVariable("id") long itemId,
//                                   @RequestParam("action") CartAction action) {
//        itemService.changeCart(userId, itemId, action);
//        return "redirect:/cart/items";
//    }
//
//    @PostMapping("/buy")
//    public String buyCart() {
//        long orderId = cartService.buyCart(userId);
//        return "redirect:/orders/" + orderId + "?newOrder=true";
//    }

}
