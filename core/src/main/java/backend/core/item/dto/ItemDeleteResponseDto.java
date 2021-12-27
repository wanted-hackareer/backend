package backend.core.item.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ItemDeleteResponseDto {
    private Long id;
    private LocalDateTime deletedAt;

    public ItemDeleteResponseDto(Long id) {
        this.id = id;
        this.deletedAt = LocalDateTime.now();
    }
}
