package backend.core.web.member.dto;

import backend.core.domain.Address;
import backend.core.domain.Member;
import lombok.Data;

@Data
public class MemberResponseDto {
    private Long id;
    private String email;
    private String nickName;
    private Address address;
    // staff 추가 하기

    public MemberResponseDto(Member entity) {
        this.id = entity.getId();
        this.email = entity.getEmail();
        this.nickName = entity.getNickName();
        this.address = entity.getAddress();
    }
}
