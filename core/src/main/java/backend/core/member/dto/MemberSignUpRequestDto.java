package backend.core.member.dto;

import backend.core.basket.domain.Basket;
import backend.core.global.domain.Address;
import backend.core.global.domain.Profile;
import backend.core.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
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

    public Member toEntity(String password, Basket basket) {
        return Member.builder()
                .email(email)
                .profile(profile)
                .password(password)
                .address(address)
                .nickName(nickName)
                .basket(basket)
                .build();
    }
}