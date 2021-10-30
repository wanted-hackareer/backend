package backend.core.web.api.ratingInfo.response;

import lombok.Data;

@Data
public class CreateRatingInfoResponse {
    private Long id;
    private Boolean isLike;
    private String opinion;
}
