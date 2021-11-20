package backend.core.service;

import backend.core.domain.Message;
import backend.core.global.error.exception.CustomException;
import backend.core.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static backend.core.dto.request.MessageRequestDto.MessageCreateRequestDto;
import static backend.core.global.error.exception.ErrorCode.MESSAGE_NOT_FOUND;

@Slf4j @Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MessageService {

    private final MessageRepository messageRepository;

    @Transactional
    public Long save(MessageCreateRequestDto dto) {
        Message message = dto.toEntity();
        messageRepository.save(message);

        return message.getId();
    }

    public Message findByIdOrThrow(Long id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new CustomException(MESSAGE_NOT_FOUND));

        return message;
    }

    public List<Message> findAllOrThrow(int offset, int limit) {
        List<Message> messages = messageRepository.findAll(offset, limit)
                .orElseThrow(() -> new CustomException(MESSAGE_NOT_FOUND));

        return messages;
    }
}

