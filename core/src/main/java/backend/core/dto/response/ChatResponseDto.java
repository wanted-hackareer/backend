package backend.core.dto.response;

import backend.core.domain.Chat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ChatResponseDto {

    private Long id;
    private String address;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;

    public ChatResponseDto(Chat entity) {
        id = entity.getId();
        address = entity.getChatAddress();
        updatedAt = entity.getUpdatedAt();
        createdAt = entity.getCreatedAt();
    }
}
