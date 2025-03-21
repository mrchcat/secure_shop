package com.github.mrchcat.intershop.item.service;

import com.github.mrchcat.intershop.item.domain.Item;
import com.github.mrchcat.intershop.item.dto.ItemDto;
import com.github.mrchcat.intershop.item.dto.MainItemsDto;
import com.github.mrchcat.intershop.item.matcher.ItemMatcher;
import com.github.mrchcat.intershop.item.repository.ItemRepository;
import com.github.mrchcat.intershop.order.domain.Order;
import com.github.mrchcat.intershop.order.domain.OrderItem;
import com.github.mrchcat.intershop.order.service.OrderService;
import com.github.mrchcat.intershop.user.domain.User;
import com.github.mrchcat.intershop.user.repository.UserRepository;
import com.github.mrchcat.intershop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Setter
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final OrderService orderService;

    @Value("${application.items.perline:3}")
    private int itemsPerLine;

    @Override
    public ItemDto getItem(long userId, long itemId) {
        Item item = itemRepository
                .findById(itemId)
                .orElseThrow(() -> new NoSuchElementException(String.format("товар c id=%s не найден", itemId)));
        return ItemMatcher.toDto(item, orderService.getBasketCountsForUser(userId));
    }

    @Override
    public MainItemsDto getItems(long userId, String search, Pageable pageable) {
        Page<Item> itemPage = (search.isBlank())
                ? itemRepository.findAll(pageable)
                : itemRepository.findAllWithSearch(search, pageable);

        List<Item> itemList = itemPage.getContent();
        List<ItemDto> itemDtoList = ItemMatcher.toDto(itemList,orderService.getBasketCountsForUser(userId));

        List<List<ItemDto>> itemDtosToShow = new ArrayList<>();
        int fullRows = itemDtoList.size() / itemsPerLine;
        for (int i = 0; i < fullRows * itemsPerLine; i = i + itemsPerLine) {
            itemDtosToShow.add(itemDtoList.subList(i, i + itemsPerLine));
        }

        itemDtosToShow.add(itemDtoList.subList(fullRows * itemsPerLine, itemDtoList.size()));
        Page<ItemDto> itemDtoPage=new PageImpl<>(itemDtoList,itemPage.getPageable(),itemPage.getTotalPages());
        return MainItemsDto.builder()
                .items(itemDtosToShow)
                .page(itemDtoPage)
                .build();
    }

    private Map<Item, Long> getBasketCountsForUser(User user) {
        Set<OrderItem> oiSet = user.getBasket().getOrderItems();
        if (oiSet.isEmpty()) {
            return Collections.emptyMap();
        }
        return oiSet.stream()
                .collect(Collectors.toMap(OrderItem::getItem, OrderItem::getQuantity));
    }

}
