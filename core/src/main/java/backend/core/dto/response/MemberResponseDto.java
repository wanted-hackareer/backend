package backend.core.dto.response;

import backend.core.domain.Address;
import backend.core.domain.Basket;
import backend.core.domain.Member;
import backend.core.domain.Profile;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MemberResponseDto {

    private Long id;
    private String email;
    private String nickName;
    private Long basketId;
    private Address address;
    private Profile profile;
    // TODO: staffList, basket 추가
    private List<StaffResponseDto> staffList;

    public MemberResponseDto(Member entity) {
        id = entity.getId();
        email = entity.getEmail();
        nickName = entity.getNickName();
        basketId = entity.getBasket().getId();
        address = entity.getAddress();
        profile = entity.getProfile();
        staffList = entity.getStaffList().stream()
                .map(staff -> new StaffResponseDto(staff))
                .collect(Collectors.toList());
    }

    @Getter
    public static class MemberSignInResponseDto {
        private Long id;
        private String email;
        private String token;

        public MemberSignInResponseDto(Member entity, String token) {
            id = entity.getId();
            email = entity.getEmail();
            this.token = token;
        }
    }
}
