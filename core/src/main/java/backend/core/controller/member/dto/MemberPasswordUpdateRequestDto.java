package backend.core.controller.member.dto;

import lombok.Data;

@Data
public class MemberPasswordUpdateRequestDto {
    private Long id;
    private String password;

    public MemberPasswordUpdateRequestDto(Long id, String password) {

        this.id = id;
        this.password = password;
    }
}
