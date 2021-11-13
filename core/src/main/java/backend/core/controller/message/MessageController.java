package backend.core.controller.message;

import backend.core.domain.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MessageController {

    @MessageMapping("/message")
    @SendTo("/topic/chat")
    public Message broadCasting(Message message) {
        log.info("message input = {}", message.getContent());
        return message;
    }
}
