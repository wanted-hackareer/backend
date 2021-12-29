package backend.core.member.dto;

import lombok.Getter;

@Getter
public class MemberSignInRequestDto {
    private String email;
    private String password;

    public MemberSignInRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
