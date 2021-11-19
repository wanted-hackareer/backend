package backend.core.dto.response;

import backend.core.domain.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {
    private String title;
    private String description;
    private String author;
    private String dayOfTheWeek;
    private String chatAddress;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PostResponseDto(Post entity) {
        this.title = entity.getTitle();
        this.description = entity.getDescription();
        this.author = entity.getMember().getNickName();
        this.dayOfTheWeek = entity.getDayOfTheWeek();
        this.chatAddress = entity.getChat().getChatAddress();
        this.updatedAt = entity.getUpdatedAt();
        this.createdAt = entity.getCreatedAt();
    }
}
