package backend.core.domain;

import backend.core.member.domain.Member;
import backend.core.post.domain.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    //== 연관관계 메서드 ==//
    public void setPost(Post post) {
        if (this.post != null) {
            this.post.getCommentList().remove(this);
        }
        this.post = post;
        post.getCommentList().add(this);
    }

    public void setMember(Member member) {
        this.member = member;
    }

    //== 비즈니스 로직 ==//
    @Builder
    public Comment(String content, Member member, Post post) {
        this.content = content;
        setMember(member);
        setPost(post);
    }

    public void update(String content) {
        this.content = content;
    }
}
