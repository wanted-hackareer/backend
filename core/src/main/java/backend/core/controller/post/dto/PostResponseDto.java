package backend.core.controller.post.dto;

import backend.core.domain.Post;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostResponseDto {
    // 수정 필요
    private String title;
    private String description;
    private String author;
    private String dayOfTheWeek;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PostResponseDto(Post entity) {
        this.title = entity.getTitle();
        this.description = entity.getDescription();
        this.author = entity.getMember().getNickName();
        this.dayOfTheWeek = entity.getDayOfTheWeek();
        this.updatedAt = entity.getUpdatedAt();
        this.createdAt = entity.getCreatedAt();
    }
}
