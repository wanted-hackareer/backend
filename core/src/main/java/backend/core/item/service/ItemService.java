package backend.core.item.service;

import backend.core.domain.Basket;
import backend.core.item.domain.Item;
import backend.core.item.dto.ItemCreateRequestDto;
import backend.core.item.dto.ItemUpdateRequestDto;
import backend.core.item.exception.ItemExistException;
import backend.core.item.exception.ItemNotFoundException;
import backend.core.item.repository.ItemRepository;
import backend.core.item.repository.ItemSearchRepository;
import backend.core.repository.ItemSearch;
import backend.core.service.BasketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemSearchRepository itemSearchRepository;
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
            throw new ItemExistException();
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
                .orElseThrow(() -> new ItemNotFoundException());
        return item;
    }

    public List<Item> findAllOrThrow(int offset, int limit) {
        List<Item> itemList = itemRepository.findAll(offset, limit)
                .orElseThrow(() -> new ItemNotFoundException());
        return itemList;
    }

    public List<Item> findAllBySearch(ItemSearch itemSearch) {
        List<Item> itemList = itemSearchRepository.findAllBySearch(itemSearch)
                .orElseThrow(() -> new ItemNotFoundException());
        return itemList;
    }
}
