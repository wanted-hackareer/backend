package backend.core.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chat extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private String chatAddress;

    @OneToMany(mappedBy = "chat")
    private List<Message> messageList = new ArrayList<>();

    //== 연관관계 메서드 ==//
    public void setPost(Post post) {
        this.post = post;
        post.setChat(this);
    }

    //== 비즈니스 로직 ==//
    @Builder
    public Chat (Post post, String chatAddress) {
        this.chatAddress = chatAddress;
        setPost(post);
    }
}
