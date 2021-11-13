package backend.core.controller.message.dto;

import backend.core.domain.Chat;
import backend.core.domain.Member;
import backend.core.domain.Message;
import backend.core.domain.MessageStatus;
import backend.core.service.MessageService;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageCreateRequestDto {
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
}
