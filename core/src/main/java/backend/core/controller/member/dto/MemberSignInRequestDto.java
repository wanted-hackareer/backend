package backend.core.controller.member.dto;

import lombok.Data;

@Data
public class MemberSignInRequestDto {
    private String email;
    private String password;
}
