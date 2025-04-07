package com.github.mrchcat.intershop.item.service;

import com.github.mrchcat.intershop.cart.service.CartService;
import com.github.mrchcat.intershop.enums.CartAction;
import com.github.mrchcat.intershop.item.domain.Item;
import com.github.mrchcat.intershop.item.dto.ItemDto;
import com.github.mrchcat.intershop.item.dto.NewItemDto;
import com.github.mrchcat.intershop.item.matcher.ItemMatcher;
import com.github.mrchcat.intershop.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Setter
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final CartService cartService;

    @Value("${application.items.images.directory}")
    private String IMAGE_DIRECTORY;

    @Value("${application.items.perline:3}")
    private int itemsPerLine;

    @Override
    public Mono<ItemDto> getItem(long userId, long itemId) {
        Mono<Item> item = itemRepository
                .findById(itemId)
                .switchIfEmpty(Mono.error(new NoSuchElementException(String.format("товар c id=%s не найден", itemId))));
        return ItemMatcher.toDto(item, cartService.getCartItemsForUser(userId));
    }

    @Override
    public Flux<Item> getItemsForOrder(Mono<Long> orderId) {
        return itemRepository.findAllForOrder(orderId);
    }

    @Override
    public Mono<Page<List<ItemDto>>> getItems(long userId, Pageable pageable, String search) {
        Flux<Item> items = (search.isBlank())
                ? itemRepository.findAllBy(pageable)
                : itemRepository.findAllWithSearch(search, pageable);

        Mono<Long> totalItems = itemRepository.count();

        Mono<List<List<ItemDto>>> itemPageContent = ItemMatcher
                .toDto(items, cartService.getCartItemsForUser(userId))
                .buffer(itemsPerLine)
                .collectList();
        return Mono
                .zip(itemPageContent, totalItems)
                .map(tuple -> new PageImpl<>(tuple.getT1(), pageable, tuple.getT2()));
    }

    @Override
    @Transactional
    public Mono<Void> changeCart(long userId, long itemId, CartAction action) {
        Mono<Item> item = itemRepository
                .findById(itemId)
                .switchIfEmpty(Mono.error(new NoSuchElementException(String.format("товар c id=%s не найден", itemId))));
        item.subscribe(System.out::println);
        return cartService.changeCart(userId, item, action);
    }

    @Override
    @Transactional
    @SneakyThrows
    public Mono<Void> downloadNewItem(Mono<NewItemDto> itemDto) {
        return itemDto
                .flatMap(this::setUuidAndImage)
                .map(ItemMatcher::toItem)
                .flatMap(itemRepository::save)
                .then();
    }

    private Mono<NewItemDto> setUuidAndImage(NewItemDto itemDto) throws IOException {
        Mono<Void> toSave = Mono.empty();
        UUID uuid;
        String imageNameToSave;
        FilePart image = itemDto.getImage();
        Path imageDerictoryPath = Path.of(IMAGE_DIRECTORY);
        if (image == null) {
            uuid = UUID.randomUUID();
            imageNameToSave = "nophoto.jpg";
        } else {
            String originalImageName = image.filename();
            int pointIndex = originalImageName.lastIndexOf('.');
            String imageExtension = (pointIndex != -1) ? originalImageName.substring(pointIndex) : "";
            Path fullImagePath;
            do {
                uuid = UUID.randomUUID();
                imageNameToSave = uuid + imageExtension;
                fullImagePath = imageDerictoryPath.resolve(imageNameToSave);
            } while (Files.exists(fullImagePath));
                Files.createFile(fullImagePath);
                toSave = DataBufferUtils.write(image.content(), fullImagePath);
        }
        itemDto.setImgPath("images/" + imageNameToSave);
        itemDto.setArticleNumber(uuid);
        return toSave.thenReturn(itemDto);
    }

}
