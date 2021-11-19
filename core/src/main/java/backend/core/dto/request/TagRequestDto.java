package backend.core.dto.request;

import backend.core.domain.Post;
import backend.core.domain.Tag;
import lombok.Getter;

public class TagRequestDto {

    @Getter
    public static class TagCreateRequestDto {
        private Long post_id;
        private String name;

        private Post post;

        public TagCreateRequestDto(Long post_id, String name) {
            this.post_id = post_id;
            this.name = name;
        }

        public Tag toEntity() {
            return Tag.builder()
                    .name(name)
                    .post(post)
                    .build();
        }

        public void setPost(Post post) {
            this.post = post;
        }
    }
}
