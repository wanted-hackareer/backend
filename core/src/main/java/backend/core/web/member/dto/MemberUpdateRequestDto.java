package backend.core.web.member.dto;

import backend.core.domain.Address;
import backend.core.domain.Profile;
import lombok.Data;

@Data
public class MemberUpdateRequestDto {
    private Long id;
    private Profile profile;
    private String nickName;
    private Address address;

    public MemberUpdateRequestDto(Long id, Profile profile, String nickName, Address address) {
        this.id = id;
        this.profile = profile;
        this.nickName = nickName;
        this.address = address;
    }
}
