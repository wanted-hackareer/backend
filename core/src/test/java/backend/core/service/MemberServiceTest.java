package backend.core.service;

import backend.core.domain.Address;
import backend.core.domain.Basket;
import backend.core.domain.Member;
import backend.core.domain.Profile;
import backend.core.global.error.exception.CustomException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static backend.core.dto.request.MemberRequestDto.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional // 테스트가 성공하면 RollBack 실행
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    @DisplayName("member save 테스트 O")
    public void save() {
        //given
        Address address = Address.builder().city("부산광역시").district("강서구").street("아무로").build();
        Profile profile = Profile.builder().storeFileName("ASDAS-asDASDAS-dsada.jpg").uploadFileName("프로필 이미지").build();

        //when
        MemberSignUpRequestDto dto = new MemberSignUpRequestDto("test10@gmail.com", "테스트10", "DF#Q$FWAD", address, profile);
        dto.setPasswordAndBasket(passwordEncoder.encode(dto.getPassword()));
        Long memberId = memberService.save(dto);

        //then
        assertThat(memberService.findByIdOrThrow(memberId).getBasket().getId()).isEqualTo(dto.getBasket().getId());
        assertThat(memberService.findByIdOrThrow(memberId).getNickName()).isEqualTo("테스트10");
        assertThat(memberService.findByIdOrThrow(memberId).getEmail()).isEqualTo("test10@gmail.com");
        assertThat(memberService.findByIdOrThrow(memberId)).isInstanceOf(Member.class);
    }

    @Test
    @DisplayName("member save 테스트 X 같은 email")
    public void saveFailSameEmail() {
        //given
        Address address = Address.builder().city("부산광역시").district("강서구").street("아무로").build();
        Profile profile = Profile.builder().storeFileName("ASDAS-asDASDAS-dsada.jpg").uploadFileName("프로필 이미지").build();

        MemberSignUpRequestDto dto = new MemberSignUpRequestDto("test12@gmail.com", "테스트12", "DF#Q$FWAD", address, profile);
        dto.setPasswordAndBasket(passwordEncoder.encode(dto.getPassword()));

        MemberSignUpRequestDto dtoSameEmail = new MemberSignUpRequestDto("test12@gmail.com", "테스트13", "ajsdhiedds", address, profile);
        dtoSameEmail.setPasswordAndBasket(passwordEncoder.encode(dtoSameEmail.getPassword()));

        //when
        memberService.save(dto);

        //then
        Assertions.assertThrows(CustomException.class,
                () -> memberService.save(dtoSameEmail));
    }

    @Test
    @DisplayName("member save 테스트 X 같은 nickname")
    public void saveFailSameNickname() {
        //given
        Address address = Address.builder().city("부산광역시").district("강서구").street("아무로").build();
        Profile profile = Profile.builder().storeFileName("ASDAS-asDASDAS-dsada.jpg").uploadFileName("프로필 이미지").build();

        MemberSignUpRequestDto dto = new MemberSignUpRequestDto("test4@gmail.com", "테스트4", "DF#Q$FWAD", address, profile);
        dto.setPasswordAndBasket(passwordEncoder.encode(dto.getPassword()));

        MemberSignUpRequestDto dtoSameNickname = new MemberSignUpRequestDto("test5@gmail.com", "테스트4", "ajsdhiedds", address, profile);
        dtoSameNickname.setPasswordAndBasket(passwordEncoder.encode(dtoSameNickname.getPassword()));
        //when
        memberService.save(dto);

        //then
        Assertions.assertThrows(CustomException.class,
                () -> memberService.save(dtoSameNickname));
    }

    @Test
    @DisplayName("member 전체 조회")
    public void findAll() {
        //given
        Address address = Address.builder().city("부산광역시").district("강서구").street("아무로").build();
        Profile profile = Profile.builder().storeFileName("ASDAS-asDASDAS-dsada.jpg").uploadFileName("프로필 이미지").build();

        MemberSignUpRequestDto dto1 = new MemberSignUpRequestDto("test2@gmail.com", "테스트2", "DF#Q$FWAD", address, profile);
        dto1.setPasswordAndBasket(passwordEncoder.encode(dto1.getPassword()));

        MemberSignUpRequestDto dto2 = new MemberSignUpRequestDto("test3@gmail.com", "테스트3", "ajsdhiedds", address, profile);
        dto2.setPasswordAndBasket(passwordEncoder.encode(dto2.getPassword()));

        //when
        memberService.save(dto1);
        memberService.save(dto2);

        //then
        assertThat(memberService.findAllOrThrow(0, 100).size()).isEqualTo(2);
    }

    @Test
    @DisplayName("member 수정 - address")
    public void memberUpdateAddress() {
        //given
        Address address = Address.builder().city("부산광역시").district("강서구").street("아무로").build();
        Address updateAddress = Address.builder().city("부산광역시").district("동래구").street("아무개").build();
        Profile profile = Profile.builder().storeFileName("ASDAS-asDASDAS-dsada.jpg").uploadFileName("프로필 이미지").build();

        MemberSignUpRequestDto dto = new MemberSignUpRequestDto("test1@gmail.com", "테스트1", "DF#Q$FWAD", address, profile);
        dto.setPasswordAndBasket(passwordEncoder.encode(dto.getPassword()));

        //when
        Long memberId = memberService.save(dto);
        Member member = memberService.findByIdOrThrow(memberId);
        MemberUpdateRequestDto memberUpdateRequestDto = new MemberUpdateRequestDto(memberId, member.getProfile(), member.getNickName(), updateAddress);
        memberService.updateOrThrow(memberUpdateRequestDto);

        //then
        assertThat(memberService.findByIdOrThrow(memberId).getAddress().getCity()).isEqualTo(updateAddress.getCity());
        assertThat(memberService.findByIdOrThrow(memberId).getAddress().getDistrict()).isEqualTo(updateAddress.getDistrict());
        assertThat(memberService.findByIdOrThrow(memberId).getAddress().getStreet()).isEqualTo(updateAddress.getStreet());
    }

    @Test
    @DisplayName("member 수정 - nickname")
    public void memberUpdateNickname() {
        //given
        Address address = Address.builder().city("부산광역시").district("강서구").street("아무로").build();
        Profile profile = Profile.builder().storeFileName("ASDAS-asDASDAS-dsada.jpg").uploadFileName("프로필 이미지").build();

        MemberSignUpRequestDto dto = new MemberSignUpRequestDto("test19@gmail.com", "테스트19", "DF#Q$FWAD", address, profile);
        dto.setPasswordAndBasket(passwordEncoder.encode(dto.getPassword()));

        //when
        Long memberId = memberService.save(dto);
        Member member = memberService.findByIdOrThrow(memberId);
        MemberUpdateRequestDto memberUpdateRequestDto = new MemberUpdateRequestDto(memberId, member.getProfile(), "수정된 닉네임", member.getAddress());
        memberService.updateOrThrow(memberUpdateRequestDto);

        //then
        assertThat(memberService.findByIdOrThrow(memberId).getNickName()).isEqualTo("수정된 닉네임");
    }

    @Test
    @DisplayName("member 수정 - profile")
    public void memberUpdateProfile() {
        //given
        Address address = Address.builder().city("부산광역시").district("강서구").street("아무로").build();
        Profile profile = Profile.builder().storeFileName("ASDAS-asDASDAS-dsada.jpg").uploadFileName("프로필 이미지").build();
        Profile updateProfile = Profile.builder().storeFileName("asddkjd-ddqwdeg-afawdqwd.jpg").uploadFileName("수정 후 프로필").build();

        MemberSignUpRequestDto dto = new MemberSignUpRequestDto("test15@gmail.com", "테스트15", "DF#Q$FWAD", address, profile);
        dto.setPasswordAndBasket(passwordEncoder.encode(dto.getPassword()));

        //when
        Long memberId = memberService.save(dto);
        Member member = memberService.findByIdOrThrow(memberId);
        MemberUpdateRequestDto memberUpdateRequestDto = new MemberUpdateRequestDto(memberId, updateProfile, member.getNickName(), member.getAddress());
        memberService.updateOrThrow(memberUpdateRequestDto);

        //then
        assertThat(memberService.findByIdOrThrow(memberId).getProfile().getUploadFileName()).isEqualTo(updateProfile.getUploadFileName());
        assertThat(memberService.findByIdOrThrow(memberId).getProfile().getStoreFileName()).isEqualTo(updateProfile.getStoreFileName());
    }

    @Test
    @DisplayName("member 수정 - password")
    public void memberUpdatePassword() {
        //given
        Address address = Address.builder().city("부산광역시").district("강서구").street("아무로").build();
        Profile profile = Profile.builder().storeFileName("ASDAS-asDASDAS-dsada.jpg").uploadFileName("프로필 이미지").build();

        MemberSignUpRequestDto dto = new MemberSignUpRequestDto("test15@gmail.com", "테스트15", "DF#Q$FWAD", address, profile);
        dto.setPasswordAndBasket(passwordEncoder.encode(dto.getPassword()));

        //when
        Long memberId = memberService.save(dto);
        MemberPasswordUpdateRequestDto memberPasswordUpdateRequestDto = new MemberPasswordUpdateRequestDto(memberId, "newPassword");
        memberService.updatePasswordOrThrow(memberPasswordUpdateRequestDto, passwordEncoder);

        //then
        Assertions.assertTrue(passwordEncoder.matches("newPassword", memberService.findByIdOrThrow(memberId).getPassword()));
    }
}