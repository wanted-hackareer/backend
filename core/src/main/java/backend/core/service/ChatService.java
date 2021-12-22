package backend.core.service;

import backend.core.domain.Chat;
import backend.core.domain.Post;
import backend.core.global.error.exception.CustomException;
import backend.core.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static backend.core.dto.request.ChatRequestDto.*;
import static backend.core.global.error.exception.ErrorCode.CHAT_NOT_FOUND;

@Slf4j @Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatService {

    private final ChatRepository chatRepository;
    private final PostService postService;

    @Transactional
    public Long save(ChatCreateRequestDto dto) {
        //TODO dto.setPost 기능 추가
        Post post = postService.findByIdOrThrow(dto.getPostId());
        Chat chat = dto.toEntity(post, UUID.randomUUID().toString());
        chatRepository.save(chat);

        return chat.getId();
    }

    public Chat findByIdOrThrow(Long id){
        Chat chat = chatRepository.findById(id)
                .orElseThrow(() -> new CustomException(CHAT_NOT_FOUND));
        return chat;
    }

    public List<Chat> findAllOrThrow(int offset, int limit) {
        List<Chat> chats = chatRepository.findAll(offset, limit)
                .orElseThrow(() -> new CustomException(CHAT_NOT_FOUND));
        return chats;
    }
}

























