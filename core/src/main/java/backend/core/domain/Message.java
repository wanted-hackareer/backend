package backend.core.domain;

import backend.core.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String content;

    @Enumerated(EnumType.STRING)
    private MessageStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id")
    private Chat chat;

    //== 연관관계 메서드 ==//
    public void setChat(Chat chat) {
        if (this.chat != null) {
            this.chat.getMessageList().remove(this);
        }
        this.chat = chat;
        chat.getMessageList().add(this);
    }

    public void setMember(Member member) {
        this.member = member;
    }

    //== 비즈니스 로직 ==//
    @Builder
    public Message (String content, Member member, Chat chat, MessageStatus status) {
        this.content = content;
        this.status = status;
        setMember(member);
        setChat(chat);
    }
}
