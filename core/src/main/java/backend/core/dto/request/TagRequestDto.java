package backend.core.dto.request;

import backend.core.domain.Post;
import backend.core.domain.Tag;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class TagRequestDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class TagCreateRequestDto {
        private Long postId;
        private String name;

        public TagCreateRequestDto(Long postId, String name) {
            this.postId = postId;
            this.name = name;
        }

        public Tag toEntity(Post post) {
            return Tag.builder()
                    .name(name)
                    .post(post)
                    .build();
        }
    }
}
