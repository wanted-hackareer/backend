package backend.core.service;

import backend.core.domain.Basket;
import backend.core.domain.Item;
import backend.core.global.error.exception.CustomException;
import backend.core.repository.ItemRepository;
import backend.core.repository.ItemSearch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static backend.core.dto.request.ItemRequestDto.ItemCreateRequestDto;
import static backend.core.dto.request.ItemRequestDto.ItemUpdateRequestDto;
import static backend.core.global.error.exception.ErrorCode.ITEM_EXIST;
import static backend.core.global.error.exception.ErrorCode.ITEM_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;
    private final BasketService basketService;

    @Transactional
    public Long save(ItemCreateRequestDto dto) {
        Basket basket = basketService.findByIdOrThrow(dto.getBasketId());
        validateItemName(basket, dto.getName());
        Item item = dto.toEntity(basket);
        itemRepository.save(item);

        return item.getId();
    }

    private void validateItemName(Basket basket, String name) {
        List<Item> itemList = basket.getItemList();
        itemList.stream().filter(item -> item.getName().equals(name)).forEachOrdered(item -> {
            throw new CustomException(ITEM_EXIST);
        });
    }

    @Transactional
    public Long update(ItemUpdateRequestDto dto) {
        Item item = findByIdOrThrow(dto.getId());
        item.update(dto.getName(), dto.getQuantity());
        return item.getId();
    }

    @Transactional
    public Long delete(Long id) {
        Item item = findByIdOrThrow(id);
        itemRepository.delete(item);
        return item.getId();
    }

    public Item findByIdOrThrow(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new CustomException(ITEM_NOT_FOUND));
        return item;
    }

    public List<Item> findAllOrThrow(int offset, int limit) {
        List<Item> itemList = itemRepository.findAll(offset, limit)
                .orElseThrow(() -> new CustomException(ITEM_NOT_FOUND));
        return itemList;
    }

    public List<Item> findAllBySearch(ItemSearch itemSearch) {
        List<Item> itemList = itemRepository.findAllBySearch(itemSearch)
                .orElseThrow(() -> new CustomException(ITEM_NOT_FOUND));
        return itemList;
    }
}
