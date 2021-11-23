package backend.core.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MessageStatus {
    ENTER("첫 입장"),
    TALK("입장 중"),
    EXIT("나가기");

    private String description;
}
