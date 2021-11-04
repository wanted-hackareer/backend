package backend.core.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RatingInfo extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Boolean isLike;
    private String opinion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rating_id")
    private Rating rating;

    //== 연관관계 메서드 ==//
    public void setRating(Rating rating) {
        if (this.rating != null) {
            this.rating.getRatingInfoList().remove(this);
        }
        this.rating = rating;
        rating.getRatingInfoList().add(this);
    }

    //== 비즈니스 로직 ==//
    @Builder
    public RatingInfo(Boolean isLike, String opinion, Rating rating) {
        this.isLike = isLike;
        this.opinion = opinion;
        this.setRating(rating);
        this.setLikeHate(isLike);
    }

    public void update(Boolean isLike, String opinion) {
        this.isLike = isLike;
        this.opinion = opinion;
    }

    private void setLikeHate(Boolean isLike) {
        if (isLike) {
            this.rating.plusLike();
        } else {
            this.rating.plusHate();
        }
    }
}
