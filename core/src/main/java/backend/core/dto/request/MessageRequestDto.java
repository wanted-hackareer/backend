package backend.core.dto.request;

import backend.core.domain.Chat;
import backend.core.domain.Member;
import backend.core.domain.Message;
import backend.core.domain.MessageStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MessageRequestDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MessageCreateRequestDto {
        private String content;
        private MessageStatus status;
        private Long memberId;
        private Long chatId;

        private Member member;
        private Chat chat;

        public MessageCreateRequestDto(String content, Long memberId, Long chatId, MessageStatus status) {
            this.content = content;
            this.memberId = memberId;
            this.chatId = chatId;
            this.status = status;
        }

        public Message toEntity() {
            return Message.builder()
                    .content(content)
                    .member(member)
                    .chat(chat)
                    .status(status)
                    .build();
        }

        public void setMemberAndChat(Member member, Chat chat) {
            this.member = member;
            this.chat = chat;
        }
    }
}
