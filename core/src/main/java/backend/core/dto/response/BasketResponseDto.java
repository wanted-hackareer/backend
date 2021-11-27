package backend.core.dto.response;

import backend.core.domain.Basket;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static backend.core.dto.response.ItemResponseDto.ItemInfoResponseDto;

@Getter
public class BasketResponseDto {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    // TODO: itemList 추가
    private List<ItemResponseDto> itemList;

    public BasketResponseDto(Basket entity) {
        id = entity.getId();
        createdAt = entity.getCreatedAt();
        updatedAt = entity.getUpdatedAt();
        itemList = entity.getItemList().stream()
                .map(item -> new ItemResponseDto(item))
                .collect(Collectors.toList());
    }

    @Getter
    public static class BasketInfoResponseDto {
        private Long id;
        private List<ItemInfoResponseDto> itemList;

        public BasketInfoResponseDto(Basket entity) {
            id = entity.getId();
            itemList = entity.getItemList().stream()
                    .map(item -> new ItemInfoResponseDto(item))
                    .collect(Collectors.toList());
        }
    }
}
