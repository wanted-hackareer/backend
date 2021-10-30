package backend.core.web.api.ratingInfo.dto;

import backend.core.domain.RatingInfo;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RatingInfoResponseDto {
    private Long id;
    private String opinion;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public RatingInfoResponseDto(RatingInfo entity) {
        id = entity.getId();
        opinion = entity.getOpinion();
        createdAt = entity.getCreatedAt();
        updatedAt = entity.getUpdatedAt();
    }
}
