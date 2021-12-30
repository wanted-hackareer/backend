package backend.core.tag.dto;

import backend.core.domain.Post;
import backend.core.tag.domain.Tag;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TagCreateRequestDto {
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