package backend.core.basket.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BasketDeleteResponseDto {
    private Long id;
    private LocalDateTime deletedAt;

    public BasketDeleteResponseDto(Long id) {
        this.id = id;
        this.deletedAt = LocalDateTime.now();
    }
}