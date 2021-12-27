package backend.core.service;

import backend.core.domain.Chat;
import backend.core.domain.Member;
import backend.core.domain.Message;
import backend.core.global.error.exception.group.MessageNotFoundException;
import backend.core.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static backend.core.dto.request.MessageRequestDto.MessageCreateRequestDto;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MessageService {

    private final MessageRepository messageRepository;
    private final MemberService memberService;
    private final ChatService chatService;

    @Transactional
    public Long save(MessageCreateRequestDto dto) {
        Member member = memberService.findByIdOrThrow(dto.getMemberId());
        Chat chat = chatService.findByIdOrThrow(dto.getChatId());

        Message message = dto.toEntity(member, chat);
        messageRepository.save(message);

        return message.getId();
    }

    public Message findByIdOrThrow(Long id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new MessageNotFoundException());

        return message;
    }

    public List<Message> findAllOrThrow(int offset, int limit) {
        List<Message> messages = messageRepository.findAll(offset, limit)
                .orElseThrow(() -> new MessageNotFoundException());

        return messages;
    }
}

