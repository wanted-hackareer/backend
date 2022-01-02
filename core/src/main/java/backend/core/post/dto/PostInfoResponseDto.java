package backend.core.post.dto;

import backend.core.post.domain.Post;
import lombok.Getter;

@Getter
public class PostInfoResponseDto {
    private Long id;
    private String title;
    private String description;
    private String dayOfTheWeek;

    public PostInfoResponseDto(Post entity) {
        id = entity.getId();
        title = entity.getTitle();
        description = entity.getDescription();
        dayOfTheWeek = entity.getDayOfTheWeek();
    }
}
