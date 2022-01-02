package backend.core.basket.dto;

import backend.core.basket.domain.Basket;
import backend.core.item.dto.ItemInfoResponseDto;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class BasketInfoResponseDto {
    private Long id;
    private List<ItemInfoResponseDto> itemList;

    public BasketInfoResponseDto(Basket entity) {
        id = entity.getId();
        itemList = entity.getItemList().stream()
                .map(item -> new ItemInfoResponseDto(item))
                .collect(Collectors.toList());
    }
}
