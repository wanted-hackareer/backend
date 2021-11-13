package backend.core.controller.staff.dto;

import backend.core.domain.StaffStatus;
import lombok.Data;

@Data
public class StaffUpdateRequestDto {
    private Long staffId;
    private StaffStatus status;

    public StaffUpdateRequestDto(Long staffId, StaffStatus status) {
        this.staffId = staffId;
        this.status = status;
    }
}
