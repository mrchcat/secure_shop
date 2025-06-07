package com.github.mrchcat.intershop.item.service;

import com.github.mrchcat.intershop.cart.service.CartService;
import com.github.mrchcat.intershop.enums.CartAction;
import com.github.mrchcat.intershop.item.domain.Item;
import com.github.mrchcat.intershop.item.dto.ItemDto;
import com.github.mrchcat.intershop.item.dto.NewItemDto;
import com.github.mrchcat.intershop.item.matcher.ItemMatcher;
import com.github.mrchcat.intershop.item.repository.ItemCachedRepository;
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

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@Setter
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final CartService cartService;
    private final ItemCachedRepository itemCachedRepository;

    @Value("${application.items.images.directory}")
    private String IMAGE_DIRECTORY;

    @Value("${application.items.perline:3}")
    private int ITEMS_PER_LINE;

    @Override
    public Mono<ItemDto> getItemDto(Optional<Long> userId, long itemId) {
        Mono<Item> item = itemCachedRepository.findById(itemId)
                .switchIfEmpty(Mono.error(new NoSuchElementException(String.format("товар c id=%s не найден", itemId))));
        var userCartItems = userId.map(cartService::getCartItemsForUser).orElse(Mono.empty());
        return ItemMatcher.toDto(item, userCartItems);
    }

    @Override
    public Mono<Page<List<ItemDto>>> getItemDtos(Optional<Long> userId, Pageable pageable, String search) {
        var items = (search.isBlank())
                ? itemCachedRepository.findAllBy(pageable)
                : itemCachedRepository.findAllWithSearch(search, pageable);

        var totalItems = itemRepository.count();
        var userCartItems = userId.map(cartService::getCartItemsForUser).orElse(Mono.empty());
        var itemPageContent = ItemMatcher
                .toDtos(items, userCartItems)
                .buffer(ITEMS_PER_LINE)
                .collectList();

        return Mono
                .zip(itemPageContent, totalItems)
                .map(tuple -> new PageImpl<>(tuple.getT1(), pageable, tuple.getT2()));
    }

    @Override
    public Flux<Item> getItemsForOrder(Mono<Long> orderId) {
        return itemRepository.findAllForOrder(orderId);
    }

    @Override
    public Flux<Item> getItemsForOrders(Flux<Long> orderIds) {
        return itemRepository.findAllForOrders(orderIds);
    }

    @Override
    @Transactional
    public Mono<Void> changeCart(long userId, long itemId, CartAction action) {
        Mono<Item> item = itemRepository
                .findById(itemId)
                .log()
                .switchIfEmpty(Mono.error(new NoSuchElementException(String.format("товар c id=%s не найден", itemId))));
        return cartService.changeCart(userId, item, action);
    }

    @Override
    @Transactional
    public Mono<Void> downloadNewItem(Mono<NewItemDto> itemDto) {
        return itemDto
                .flatMap(this::setUuidAndImage)
                .map(ItemMatcher::toItem)
                .flatMap(itemRepository::save)
                .then();
    }

    @SneakyThrows
    private Mono<NewItemDto> setUuidAndImage(NewItemDto itemDto) {
        Mono<Void> toSave = Mono.empty();
        UUID uuid = getNewUUID();
        String imageNameToSave;
        FilePart image = itemDto.getImage();
        Path imageDerictoryPath = Path.of(IMAGE_DIRECTORY);
        if (image == null) {
            imageNameToSave = "nophoto.jpg";
        } else {
            String originalImageName = image.filename();
            int pointIndex = originalImageName.lastIndexOf('.');
            String imageExtension = (pointIndex != -1) ? originalImageName.substring(pointIndex) : "";
            Path fullImagePath;
            imageNameToSave = uuid + imageExtension;
            fullImagePath = imageDerictoryPath.resolve(imageNameToSave);
            Files.createFile(fullImagePath);
            toSave = DataBufferUtils.write(image.content(), fullImagePath);
        }
        itemDto.setImgPath("images/" + imageNameToSave);
        itemDto.setArticleNumber(uuid);
        return toSave.thenReturn(itemDto);
    }

    @SneakyThrows
    private UUID getNewUUID() {
        UUID uuid;
        Boolean hasUuid;
        do {
            uuid = UUID.randomUUID();
            hasUuid = itemRepository.hasUuid(uuid).toFuture().get();
        } while (Boolean.TRUE.equals(hasUuid));
        return uuid;
    }
}
