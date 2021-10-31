package backend.core.web.api.ratingInfo.dto;

import backend.core.domain.Rating;
import backend.core.domain.RatingInfo;
import lombok.*;

@Data
public class RequestCreateDto {

    private String opinion;
    private Rating rating;
    private Boolean isLike;
    private Long ratingId;

    public RequestCreateDto(String opinion, Boolean isLike, Long ratingId) {
        this.opinion = opinion;
        this.isLike = isLike;
        this.ratingId = ratingId;
    }

    public RatingInfo toEntity() {
        return RatingInfo.builder()
                .rating(rating)
                .opinion(opinion)
                .isLike(isLike)
                .build();
    }

    @Override
    public String toString() {
        return "RatingInfoRequestCreateDto{" +
                "opinion='" + opinion + '\'' +
                ", rating=" + rating +
                ", isLike=" + isLike +
                ", ratingId=" + ratingId +
                '}';
    }
}
