package com.github.mrchcat.intershop.item.service;

import com.github.mrchcat.intershop.cart.service.CartService;
import com.github.mrchcat.intershop.enums.CartAction;
import com.github.mrchcat.intershop.item.domain.Item;
import com.github.mrchcat.intershop.item.dto.ItemDto;
import com.github.mrchcat.intershop.item.dto.MainItemsDto;
import com.github.mrchcat.intershop.item.dto.NewItemDto;
import com.github.mrchcat.intershop.item.matcher.ItemMatcher;
import com.github.mrchcat.intershop.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Setter
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final CartService cartService;
    private final Path IMAGE_DIRECTORY_PATH = Path.of("src/main/resources/static");
    private final Path IMAGE_DIRECTORY_NAME = Path.of("images");

    @Value("${application.items.perline:3}")
    private int itemsPerLine;

    @Override
    public ItemDto getItem(long userId, long itemId) {
        Item item = itemRepository
                .findById(itemId)
                .orElseThrow(() -> new NoSuchElementException(String.format("товар c id=%s не найден", itemId)));
        return ItemMatcher.toDto(item, cartService.getCartItemsForUser(userId));
    }

    @Override
    public MainItemsDto getItems(long userId, String search, Pageable pageable) {
        Page<Item> itemPage = (search.isBlank())
                ? itemRepository.findAll(pageable)
                : itemRepository.findAllWithSearch(search, pageable);

        List<Item> itemList = itemPage.getContent();
        List<ItemDto> itemDtoList = ItemMatcher.toDto(itemList, cartService.getCartItemsForUser(userId));

        List<List<ItemDto>> itemDtosToShow = new ArrayList<>();
        int fullRows = itemDtoList.size() / itemsPerLine;
        for (int i = 0; i < fullRows * itemsPerLine; i = i + itemsPerLine) {
            itemDtosToShow.add(itemDtoList.subList(i, i + itemsPerLine));
        }
        itemDtosToShow.add(itemDtoList.subList(fullRows * itemsPerLine, itemDtoList.size()));
        return MainItemsDto.builder()
                .items(itemDtosToShow)
                .page(itemPage)
                .build();
    }

    @Override
    @Transactional
    public void changeCart(long userId, long itemId, CartAction action) {
        Item item = itemRepository
                .findById(itemId)
                .orElseThrow(() -> new NoSuchElementException(String.format("товар c id=%s не найден", itemId)));
        cartService.changeCart(userId, item, action);
    }

    @Override
    @Transactional
    @SneakyThrows
    public void downloadNewItem(NewItemDto itemDto) {
        setUuidAndImage(itemDto);
        Item newItem = ItemMatcher.toItem(itemDto);
        itemRepository.save(newItem);
    }

    private void setUuidAndImage(NewItemDto itemDto) throws IOException {
        UUID uuid;
        String imageNameToSave;
        MultipartFile image = itemDto.getImage();
        if(image==null){
            uuid = UUID.randomUUID();
            imageNameToSave="nophoto.jpg";
        } else {
            String originalImageName = itemDto.getImage().getOriginalFilename();
            int pointIndex = originalImageName.lastIndexOf('.');
            String imageExtention = (pointIndex != -1) ? originalImageName.substring(pointIndex) : "";
            Path fullImagePath;
            do {
                uuid = UUID.randomUUID();
                imageNameToSave = uuid + imageExtention;
                fullImagePath = IMAGE_DIRECTORY_PATH.resolve(IMAGE_DIRECTORY_NAME).resolve(imageNameToSave);
            } while (Files.exists(fullImagePath));
            Files.createFile(fullImagePath);
            image.transferTo(fullImagePath);
        }
        itemDto.setImgPath(IMAGE_DIRECTORY_NAME + "/" + imageNameToSave);
        itemDto.setArticleNumber(uuid);
    }

}
