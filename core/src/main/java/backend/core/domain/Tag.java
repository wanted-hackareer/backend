package backend.core.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    //== 연관관계 메서드 ==//
    public void setPost(Post post) {
        if (post != null) {
            this.post.getTagList().remove(this);
        }
        this.post = post;
        post.getTagList().add(this);
    }

    //== 비즈니스 로직 ==//
    @Builder
    public Tag(Long id, String name, Post post) {
        this.id = id;
        this.name = name;
        setPost(post);
    }
}
