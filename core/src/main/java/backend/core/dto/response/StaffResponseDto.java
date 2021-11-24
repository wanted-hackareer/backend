package backend.core.dto.response;

import backend.core.domain.Staff;
import backend.core.domain.StaffStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StaffResponseDto {
    private Long id;
    private Long memberId;
    private StaffStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public StaffResponseDto(Staff entity) {
        id = entity.getId();
        memberId = entity.getMember().getId();
        status = entity.getStaffStatus();
        createdAt = entity.getCreatedAt();
        updatedAt = entity.getUpdatedAt();
    }
}
