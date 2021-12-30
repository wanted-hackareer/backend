package backend.core.tag.dto;

import backend.core.tag.domain.Tag;
import lombok.Getter;

@Getter
public class TagInfoResponseDto {
    private Long id;
    private String name;

    public TagInfoResponseDto(Tag entity) {
        id = entity.getId();
        name = entity.getName();
    }
}