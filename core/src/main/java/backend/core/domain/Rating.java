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
public class Rating extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Long likes;
    private Long hates;

    @OneToMany(mappedBy = "rating")
    private List<RatingInfo> ratingInfoList = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    //== 연관관계 메서드 ==//
    public void setMember(Member member) {
        this.member = member;
    }

    //== 비즈니스 로직 ==//
    @Builder
    public Rating (Member member) {
        this.likes = 0L;
        this.hates = 0L;
        setMember(member);
    }

    public void plusLike() {
        this.likes += 1;
    }

    public void plusHate() {
        this.hates += 1;
    }
}
