package backend.core.web.api.ratingInfo.dto;

import backend.core.domain.Rating;
import backend.core.domain.RatingInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RatingInfoRequestCreateDto {

    private String opinion;
    private Rating rating;
    private Boolean isLike;

    @Builder
    public RatingInfoRequestCreateDto(String opinion, Rating rating, Boolean isLike) {
        this.opinion = opinion;
        this.rating = rating;
        this.isLike = isLike;
    }

    public RatingInfo toEntity(){
        return RatingInfo.builder()
                .isLike(isLike)
                .opinion(opinion)
                .rating(rating)
                .build();
    }
}
