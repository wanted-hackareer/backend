package backend.core.dto.response;

import backend.core.domain.Tag;
import lombok.Getter;

@Getter
public class TagResponseDto {
    private Long id;
    private String name;

    public TagResponseDto(Tag entity) {
        id = entity.getId();
        name = entity.getName();
    }
}
