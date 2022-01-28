package backend.core.item.service;

import backend.core.item.domain.Item;
import backend.core.item.exception.ItemNotFoundException;
import backend.core.item.repository.ItemSearchRepository;
import backend.core.item.repository.ItemSearch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemSearchService {

    private final ItemSearchRepository itemSearchRepository;

    public List<Item> findAllBySearch(ItemSearch itemSearch) {
        List<Item> itemList = itemSearchRepository.findAllBySearch(itemSearch)
                .orElseThrow(() -> new ItemNotFoundException());
        return itemList;
    }
}
