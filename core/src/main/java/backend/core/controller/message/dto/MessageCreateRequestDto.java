package backend.core.controller.message.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageCreateRequestDto {
    private String content;
    private Long memberId;
    private Long chatId;

    public MessageCreateRequestDto(String content, Long memberId, Long chatId) {
        this.content = content;
        this.memberId = memberId;
        this.chatId = chatId;
    }
}
