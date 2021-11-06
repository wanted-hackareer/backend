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

    private Integer maximum;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private PostStatus postStatus;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "post")
    private Chat chat;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "post")
    private Basket basket;

    @OneToMany(mappedBy = "post")
    private List<Staff> staffList = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Tag> tagList = new ArrayList<>();

    //== 연관관계 메서드 ==//
    public void setMember(Member member) {
        this.member = member;

        Staff staff = Staff.builder().post(this).member(member).build();
        this.staffList.add(staff);
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }

    //== 비즈니스 로직 ==//
    @Builder
    public Post(String title, String description, int maximum, Member member) {
        this.title = title;
        this.description = description;
        this.maximum = maximum;
        this.postStatus = PostStatus.ACCESS;
        this.address = member.getAddress();
        setMember(member);
    }

    public void update(String title, String description, int maximum, PostStatus status) {
        this.title = title;
        this.description = description;
        this.maximum = maximum;
        this.postStatus = status;
    }
}
