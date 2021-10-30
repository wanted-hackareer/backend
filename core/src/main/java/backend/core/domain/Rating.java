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

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "rating")
    private Member member;

    //== 비즈니스 로직 ==//
    @Builder
    public Rating (Member member) {
        this.likes = 0L;
        this.hates = 0L;
        this.member = member;
    }

    public void plusLike() {
        this.likes += 1;
    }

    public void plusHate() {
        this.hates += 1;
    }
}
