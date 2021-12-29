package backend.core.member.dto;

import lombok.Getter;

@Getter
public class MemberPasswordUpdateRequestDto {
    private Long id;
    private String password;

    public MemberPasswordUpdateRequestDto(Long id, String password) {
        this.id = id;
        this.password = password;
    }
}
