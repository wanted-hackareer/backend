package backend.core.service;

import backend.core.domain.Chat;
import backend.core.global.error.exception.CustomException;
import backend.core.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static backend.core.dto.request.ChatRequestDto.*;
import static backend.core.global.error.exception.ErrorCode.CHAT_NOT_FOUND;

@Slf4j @Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatService {

    private final ChatRepository chatRepository;

    @Transactional
    public Long save(ChatCreateRequestDto dto) {
/*        Post post = postService.findById(dto.getPostId());
        String chatAddress = UUID.randomUUID().toString();

        dto.setPost(post);
        dto.setChatAddress(chatAddress);*/

        Chat chat = dto.toEntity();
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

























