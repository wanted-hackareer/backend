package backend.core.service;

import backend.core.domain.Chat;
import backend.core.global.error.exception.group.ChatNotFoundException;
import backend.core.post.service.PostService;
import backend.core.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static backend.core.dto.request.ChatRequestDto.ChatCreateRequestDto;

@Slf4j
@Service
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

    public Chat findByIdOrThrow(Long id) {
        Chat chat = chatRepository.findById(id)
                .orElseThrow(() -> new ChatNotFoundException());
        return chat;
    }

    public List<Chat> findAllOrThrow(int offset, int limit) {
        List<Chat> chats = chatRepository.findAll(offset, limit)
                .orElseThrow(() -> new ChatNotFoundException());
        return chats;
    }
}

























