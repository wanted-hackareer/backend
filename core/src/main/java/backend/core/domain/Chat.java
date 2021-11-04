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

    @OneToMany(mappedBy = "chat")
    private List<Message> messageList = new ArrayList<>();

    //== 연관관계 메서드 ==//
    public void setPost(Post post) {
        this.post = post;
    }

    //== 비즈니스 로직 ==//
    @Builder
    public Chat (Post post) {
        setPost(post);
    }
}
