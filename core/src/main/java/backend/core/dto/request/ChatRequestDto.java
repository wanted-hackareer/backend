package backend.core.dto.request;

import backend.core.domain.Chat;
import backend.core.post.domain.Post;
import lombok.Getter;

public class ChatRequestDto {

    @Getter
    public static class ChatCreateRequestDto {
        private Long postId;

        public ChatCreateRequestDto(Long postId) {
            this.postId = postId;
        }

        public Chat toEntity(Post post, String chatAddress) {
            return Chat.builder()
                    .post(post)
                    .chatAddress(chatAddress)
                    .build();
        }
    }
}
