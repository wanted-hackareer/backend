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
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String description;

    private Long maximum;
    private Long participants;

    @Embedded
    private Address address;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Chat chat;

    @OneToMany(mappedBy = "post")
    private List<Staff> staffList = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Comment> commentList = new ArrayList<>();

    //== 연관관계 메서드 ==/
    public void setChat(Chat chat) {
        this.chat = chat;
    }

    //== 비즈니스 로직 ==//
    @Builder
    public Post(String title, String description, Long maximum, Long participants, Address address, Chat chat) {
        this.title = title;
        this.description = description;
        this.maximum = maximum;
        this.participants = participants;
        this.address = address;
        setChat(chat);
    }
}
