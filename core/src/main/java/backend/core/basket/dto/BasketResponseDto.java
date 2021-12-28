package backend.core.basket.dto;

import backend.core.basket.domain.Basket;
import backend.core.item.dto.ItemResponseDto;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class BasketResponseDto {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<ItemResponseDto> itemList;

    public BasketResponseDto(Basket entity) {
        id = entity.getId();
        createdAt = entity.getCreatedAt();
        updatedAt = entity.getUpdatedAt();
        itemList = entity.getItemList().stream()
                .map(item -> new ItemResponseDto(item))
                .collect(Collectors.toList());
    }
}
