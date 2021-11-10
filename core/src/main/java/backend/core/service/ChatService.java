package backend.core.service;

import backend.core.controller.chat.dto.ChatCreateRequestDto;
import backend.core.domain.Chat;
import backend.core.domain.Post;
import backend.core.global.error.exception.CustomException;
import backend.core.repository.ChatRepository;
import backend.core.repository.PostRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static backend.core.global.error.exception.ErrorCode.POST_NOT_FOUND;

@Slf4j @Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatService {

    private final ChatRepository chatRepository;
    private final PostRepository postRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public Long save(ChatCreateRequestDto dto) {
        Post post = postRepository.findById(dto.getPostId())
                .orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        String chatAddress = UUID.randomUUID().toString();

        dto.setPost(post);
        dto.setChatAddress(chatAddress);

        Chat chat = dto.toEntity();
        chatRepository.save(chat);

        return chat.getId();
    }
}
