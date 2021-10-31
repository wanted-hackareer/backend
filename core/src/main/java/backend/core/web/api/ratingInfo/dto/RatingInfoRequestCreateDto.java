package backend.core.web.api.ratingInfo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RatingInfoRequestCreateDto {

    private String opinion;
    private Long ratingId;
    private Boolean isLike;

    @Builder
    public RatingInfoRequestCreateDto(String opinion, Long ratingId, Boolean isLike) {
        this.opinion = opinion;
        this.ratingId = ratingId;
        this.isLike = isLike;
    }

    @Override
    public String toString() {
        return "RatingInfoRequestCreateDto{" +
                "opinion='" + opinion + '\'' +
                ", ratingId=" + ratingId +
                ", isLike=" + isLike +
                '}';
    }
}
