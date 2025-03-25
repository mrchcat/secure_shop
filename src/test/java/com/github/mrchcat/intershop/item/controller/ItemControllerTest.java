package com.github.mrchcat.intershop.item.controller;

import com.github.mrchcat.intershop.enums.CartAction;
import com.github.mrchcat.intershop.item.domain.Item;
import com.github.mrchcat.intershop.item.repository.ItemRepository;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testMain() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("main/items"));
    }

    @Test
    void testGetItem() throws Exception {
        long itemId = 1;
        mockMvc.perform(get("/items/" + itemId))
                .andExpect(status().isOk())
                .andExpect(view().name("item"))
                .andExpect(model().attributeExists("item"));
    }

    @Test
    void testGetItems() throws Exception {
        mockMvc.perform(get("/main/items"))
                .andExpect(status().isOk())
                .andExpect(view().name("main"))
                .andExpect(model().attributeExists("paging"))
                .andExpect(model().attributeExists("items"))
                .andExpect(model().attributeExists("sort"));
    }

    @Test
    void testUpdateCartPlus() throws Exception {
        long itemId = 1;
        mockMvc.perform(post("/main/items/" + itemId)
                        .param("action", CartAction.plus.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/main/items"));
    }

    @Test
    void testUpdateCartMinus() throws Exception {
        long itemId = 1;
        mockMvc.perform(post("/main/items/" + itemId)
                        .param("action", CartAction.plus.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/main/items"));
        mockMvc.perform(post("/main/items/" + itemId)
                        .param("action", CartAction.minus.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/main/items"));
    }

    @Test
    void testUpdateCartDelete() throws Exception {
        long itemId = 1;
        mockMvc.perform(post("/main/items/" + itemId)
                        .param("action", CartAction.delete.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/main/items"));
    }
}