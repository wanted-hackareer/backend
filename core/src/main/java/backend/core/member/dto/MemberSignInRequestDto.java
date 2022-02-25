package backend.core.member.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberSignInRequestDto {
    private String email;
    private String password;

    public MemberSignInRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
