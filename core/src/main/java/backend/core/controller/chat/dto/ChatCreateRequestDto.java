package backend.core.controller.chat.dto;

import backend.core.domain.Chat;
import backend.core.domain.Post;
import lombok.Data;

@Data
public class ChatCreateRequestDto {
    private Long postId;

    private String chatAddress;
    private Post post;

    public ChatCreateRequestDto(Long postId) {
        this.postId = postId;
    }

    public Chat toEntity() {
        return Chat.builder()
                .post(post)
                .chatAddress(chatAddress)
                .build();
    }
}