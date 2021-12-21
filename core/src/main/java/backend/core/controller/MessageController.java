package backend.core.controller;

import backend.core.domain.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MessageController {

    @MessageMapping("/message")
    @SendTo("/topic/chat/{id}")
    public Message broadCasting(
            Message message,
            @PathVariable String chatId) {
        log.info("message input = {}, chatId = {}", message.getContent(), chatId);
        return message;
    }
}
