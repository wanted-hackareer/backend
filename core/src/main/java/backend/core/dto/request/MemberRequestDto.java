package backend.core.dto.request;

import backend.core.domain.Address;
import backend.core.domain.Member;
import backend.core.domain.Profile;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author euise
 *
 * 멤버 요청 api 정리 클래스
 */
public class MemberRequestDto {

    @Data
    public static class MemberPasswordUpdateRequestDto {
        private Long id;
        private String password;

        public MemberPasswordUpdateRequestDto (Long id, String password) {
            this.id = id;
            this.password = password;
        }
    }

    @Data
    public static class MemberSignInRequestDto {
        private String email;
        private String password;
    }

    @Data
    public static class MemberUpdateRequestDto {
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

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MemberSignUpRequestDto {
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
}
