package backend.core.dto.response;

import backend.core.domain.Item;
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
        this.id = entity.getId();
        this.name = entity.getName();
        this.quantity = entity.getQuantity();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
    }
}
