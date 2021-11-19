package backend.core.dto.request;

import backend.core.domain.Chat;
import backend.core.domain.Post;
import lombok.Getter;

public class ChatRequestDto {

    @Getter
    public static class ChatCreateRequestDto {
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

        public void setPost(Post post) {
            this.post = post;
        }
    }
}
