package backend.core.Staff.dto;

import backend.core.Staff.domain.Staff;
import backend.core.global.domain.Address;
import lombok.Getter;

@Getter
public class StaffPostInfoResponseDto {
    private Long id;
    private String title;
    private String description;
    private Address address;

    public StaffPostInfoResponseDto(Staff entity) {
        id = entity.getId();
        title = entity.getPost().getTitle();
        description = entity.getPost().getDescription();
        address = entity.getPost().getAddress();
    }
}