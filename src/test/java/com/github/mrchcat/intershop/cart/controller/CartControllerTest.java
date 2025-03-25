package com.github.mrchcat.intershop.cart.controller;

import com.github.mrchcat.intershop.enums.CartAction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetCart() throws Exception {
        mockMvc.perform(get("/cart/items"))
                .andExpect(status().isOk())
                .andExpect(view().name("cart"))
                .andExpect(model().attributeExists("items"))
                .andExpect(model().attributeExists("total"))
                .andExpect(model().attributeExists("empty"));
    }

    @Test
    void testUpdateCartPlus() throws Exception {
        long itemId = 1;
        mockMvc.perform(post("/cart/items/" + itemId)
                        .param("action", CartAction.plus.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/cart/items"));
    }

    @Test
    void testUpdateCartMinus() throws Exception {
        long itemId = 1;
        mockMvc.perform(post("/cart/items/" + itemId)
                        .param("action", CartAction.plus.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/cart/items"));
        mockMvc.perform(post("/cart/items/" + itemId)
                        .param("action", CartAction.minus.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/cart/items"));
    }

    @Test
    void testUpdateCartDelete() throws Exception {
        long itemId = 1;
        mockMvc.perform(post("/cart/items/" + itemId)
                        .param("action", CartAction.delete.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/cart/items"));
    }

    @Test
    void testBuy() throws Exception {
        long itemId = 1;
        mockMvc.perform(post("/cart/items/" + itemId)
                        .param("action", CartAction.plus.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/cart/items"));
        mockMvc.perform(post("/buy"))
                .andExpect(status().is3xxRedirection());
    }


}