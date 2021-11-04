package backend.core.web.member.dto;

import backend.core.domain.Member;
import lombok.Data;

@Data
public class MemberSignInResponseDto {
    private Long id;
    private String email;
    private String token;

    public MemberSignInResponseDto (Member entity, String token) {
        this.id = entity.getId();
        this.email = entity.getEmail();
        this.token = token;
    }
}
