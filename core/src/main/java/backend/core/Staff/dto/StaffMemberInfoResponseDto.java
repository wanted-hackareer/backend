package backend.core.Staff.dto;

import backend.core.Staff.domain.Staff;
import backend.core.global.domain.Address;
import lombok.Getter;

@Getter
public class StaffMemberInfoResponseDto {
    private Long id;
    private String nickname;
    private Address address;

    public StaffMemberInfoResponseDto(Staff entity) {
        id = entity.getId();
        nickname = entity.getMember().getNickName();
        address = entity.getMember().getAddress();
    }
}
