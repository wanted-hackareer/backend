package backend.core.tag.domain;

import backend.core.domain.BaseTimeEntity;
import backend.core.domain.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    //== 연관관계 메서드 ==//
    public void setPost(Post post) {
        if (this.post != null) {
            this.post.getTagList().remove(this);
        }
        this.post = post;
        post.getTagList().add(this);
    }

    //== 비즈니스 로직 ==//
    @Builder
    public Tag(String name, Post post) {
        this.name = name;
        setPost(post);
    }

    public void update(String name) {
        this.name = name;
    }
}