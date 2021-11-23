package backend.core.dto.response;

import backend.core.domain.Basket;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class BasketResponseDto {
    private Long id;
    private String owner;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    // TODO: itemList 추가
    private List<ItemResponseDto> items;

    public BasketResponseDto(Basket entity) {
        id = entity.getId();
        owner = entity.getMember().getNickName();
        createdAt = entity.getCreatedAt();
        updatedAt = entity.getUpdatedAt();
        items = entity.getItemList().stream()
                .map(item -> new ItemResponseDto(item))
                .collect(Collectors.toList());
    }
}
