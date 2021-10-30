package backend.core.web.api.ratingInfo.request;

import lombok.Data;

@Data
public class CreateRatingInfoRequest {
    private Long ratingId;
    private Boolean isLike;
    private String opinion;
}
