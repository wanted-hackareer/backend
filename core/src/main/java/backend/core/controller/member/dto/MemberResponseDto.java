package backend.core.controller.member.dto;

import backend.core.domain.Address;
import backend.core.domain.Member;
import backend.core.domain.Profile;
import lombok.Data;

@Data
public class MemberResponseDto {
    private Long id;
    private String email;
    private String nickName;
    private Address address;
    private Profile profile;
    // staff, profile 추가하기

    public MemberResponseDto(Member entity) {
        this.id = entity.getId();
        this.email = entity.getEmail();
        this.nickName = entity.getNickName();
        this.address = entity.getAddress();
        this.profile = entity.getProfile();
    }
}
