package backend.core.tag.dto;

import backend.core.tag.domain.Tag;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TagResponseDto {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public TagResponseDto(Tag entity) {
        id = entity.getId();
        name = entity.getName();
        createdAt = entity.getCreatedAt();
        updatedAt = entity.getUpdatedAt();
    }
}
