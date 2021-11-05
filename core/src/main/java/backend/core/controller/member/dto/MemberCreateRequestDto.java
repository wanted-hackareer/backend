package backend.core.controller.member.dto;

import backend.core.domain.Address;
import backend.core.domain.Member;
import backend.core.domain.Profile;
import lombok.Data;

@Data
public class MemberCreateRequestDto {
    private String email;
    private String nickName;
    private Address address;
    private Profile profile;

    public MemberCreateRequestDto(String email, String nickName, Address address, Profile profile) {
        this.email = email;
        this.nickName = nickName;
        this.address = address;
        this.profile = profile;
    }

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .profile(profile)
                .address(address)
                .nickName(nickName)
                .build();
    }
}
