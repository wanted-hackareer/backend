package backend.core.controller.member.dto;

import backend.core.domain.Address;
import backend.core.domain.Member;
import backend.core.domain.Profile;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberSignUpRequestDto {
    private String email;
    private String nickName;
    private String password;
    private Address address;
    private Profile profile;

    public MemberSignUpRequestDto(String email, String nickName, String password, Address address, Profile profile) {
        this.email = email;
        this.nickName = nickName;
        this.address = address;
        this.profile = profile;
        this.password = password;
    }

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .profile(profile)
                .password(password)
                .address(address)
                .nickName(nickName)
                .build();
    }
}
