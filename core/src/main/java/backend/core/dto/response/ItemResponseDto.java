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
        id = entity.getId();
        name = entity.getName();
        quantity = entity.getQuantity();
        createdAt = entity.getCreatedAt();
        updatedAt = entity.getUpdatedAt();
    }

    @Getter
    public static class ItemInfoResponseDto {
        private Long id;
        private String name;
        private int quantity;

        public ItemInfoResponseDto(Item entity) {
            id = entity.getId();
            name = entity.getName();
            quantity = entity.getQuantity();
        }
    }

    @Getter
    public static class ItemDeleteResponseDto {
        private Long id;
        private LocalDateTime deletedAt;

        public ItemDeleteResponseDto(Long id) {
            this.id = id;
            this.deletedAt = LocalDateTime.now();
        }
    }
}
