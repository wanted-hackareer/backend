package backend.core.member.dto;

import backend.core.basket.dto.BasketResponseDto;
import backend.core.domain.Address;
import backend.core.domain.Profile;
import backend.core.member.domain.Member;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

import static backend.core.dto.response.StaffResponseDto.StaffPostInfoResponseDto;

@Getter
public class MemberResponseDto {

    private Long id;
    private String email;
    private String nickName;
    private Address address;
    private Profile profile;
    // TODO: staffList, basket 추가
    private BasketResponseDto basket;
    private List<StaffPostInfoResponseDto> staffList;

    public MemberResponseDto(Member entity) {
        id = entity.getId();
        email = entity.getEmail();
        nickName = entity.getNickName();
        address = entity.getAddress();
        profile = entity.getProfile();

        basket = new BasketResponseDto(entity.getBasket());
        staffList = entity.getStaffList().stream()
                .map(staff -> new StaffPostInfoResponseDto(staff))
                .collect(Collectors.toList());
    }
}