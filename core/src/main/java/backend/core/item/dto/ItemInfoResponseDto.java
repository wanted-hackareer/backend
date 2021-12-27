package backend.core.item.dto;

import backend.core.item.domain.Item;
import lombok.Getter;

@Getter
public class ItemInfoResponseDto {
    private Long id;
    private String name;
    private int quantity;

    public ItemInfoResponseDto(Item entity) {
        id = entity.getId();
        name = entity.getName();
        quantity = entity.getQuantity();
    }
}
