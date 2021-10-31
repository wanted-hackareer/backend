package backend.core.web.api.ratingInfo.dto;

import backend.core.domain.RatingInfo;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseDto {
    private Long id;
    private String opinion;
    private Boolean isLike;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ResponseDto(RatingInfo entity) {
        id = entity.getId();
        opinion = entity.getOpinion();
        isLike = entity.getIsLike();
        createdAt = entity.getCreatedAt();
        updatedAt = entity.getUpdatedAt();
    }
}
