package backend.core.tag.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TagDeleteResponseDto {
    private Long id;
    private LocalDateTime deletedAt;

    public TagDeleteResponseDto(Long id) {
        this.id = id;
        this.deletedAt = LocalDateTime.now();
    }
}