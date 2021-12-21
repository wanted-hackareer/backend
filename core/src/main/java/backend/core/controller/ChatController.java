package backend.core.controller;

import backend.core.domain.Chat;
import backend.core.domain.Post;
import backend.core.dto.response.ChatResponseDto;
import backend.core.service.ChatService;
import backend.core.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

import static backend.core.dto.request.ChatRequestDto.ChatCreateRequestDto;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ChatController {

    private final PostService postService;
    private final ChatService chatService;

    @PostMapping("/chat")
    public ChatResponseDto chatV1(
            @Valid @RequestBody ChatCreateRequestDto dto) {
        Post post = postService.findByIdOrThrow(dto.getPostId());
        dto.setPostAndChatAddress(post, UUID.randomUUID().toString());

        Long id = chatService.save(dto);
        Chat chat = chatService.findByIdOrThrow(id);
        return new ChatResponseDto(chat);
    }
}
