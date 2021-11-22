package backend.core.dto.response;

import backend.core.domain.Basket;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BasketResponseDto {
    private Long id;
    private String owner;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    // TODO: itemList 추가

    public BasketResponseDto(Basket entity) {
        this.id = entity.getId();
        this.owner = entity.getMember().getNickName();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
    }
}
