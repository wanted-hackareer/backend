package backend.core.web.api.ratingInfo.dto;

import lombok.*;

@Data
public class RequestUpdateDto {
    private Long id;
    private String opinion;
    private Boolean isLike;

    public RequestUpdateDto(String opinion, Boolean isLike) {
        this.opinion = opinion;
        this.isLike = isLike;
    }
}

