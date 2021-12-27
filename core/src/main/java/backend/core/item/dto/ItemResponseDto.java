package backend.core.item.dto;

import backend.core.item.domain.Item;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ItemResponseDto {
    private Long id;
    private String name;
    private int quantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ItemResponseDto(Item entity) {
        id = entity.getId();
        name = entity.getName();
        quantity = entity.getQuantity();
        createdAt = entity.getCreatedAt();
        updatedAt = entity.getUpdatedAt();
    }
}
