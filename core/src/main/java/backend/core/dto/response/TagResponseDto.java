package backend.core.dto.response;

import backend.core.domain.Tag;
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

    @Getter
    public static class TagInfoResponseDto {
        private Long id;
        private String name;

        public TagInfoResponseDto(Tag entity) {
            id = entity.getId();
            name = entity.getName();
        }
    }

    @Getter
    public static class TagDeleteResponseDto {
        private Long id;
        private LocalDateTime deletedAt;

        public TagDeleteResponseDto(Long id) {
            this.id = id;
            this.deletedAt = LocalDateTime.now();
        }
    }
}
