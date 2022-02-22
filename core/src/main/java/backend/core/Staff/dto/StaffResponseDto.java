package backend.core.Staff.dto;

import backend.core.Staff.domain.Staff;
import backend.core.Staff.domain.StaffStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StaffResponseDto {
    private Long id;
    private Long memberId;
    private Long postId;
    private StaffStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public StaffResponseDto(Staff entity) {
        id = entity.getId();
        memberId = entity.getMember().getId();
        postId = entity.getPost().getId();
        status = entity.getStaffStatus();
        createdAt = entity.getCreatedAt();
        updatedAt = entity.getUpdatedAt();
    }
}
