package backend.core.member.dto;

import backend.core.member.domain.Member;
import lombok.Getter;

@Getter
public class MemberSignInResponseDto {
    private Long id;
    private String email;
    private String token;

    public MemberSignInResponseDto(Member entity, String token) {
        id = entity.getId();
        email = entity.getEmail();
        this.token = token;
    }
}
