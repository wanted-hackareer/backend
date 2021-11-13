package backend.core.controller.post.dto;

import backend.core.domain.PostStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostUpdateRequestDto {
    private Long postId;
    private String title;
    private String description;
    private String dayOfTheWeek;
    private int maximum;
    private PostStatus status;

    public PostUpdateRequestDto(Long postId, String title, String description, int maximum, PostStatus status, String dayOfTheWeek) {
        this.title = title;
        this.postId = postId;
        this.description = description;
        this.maximum = maximum;
        this.status = status;
        this.dayOfTheWeek = dayOfTheWeek;
    }
}
