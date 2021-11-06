package backend.core.domain;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberTest {

    @Test
    @DisplayName("member 생성")
    public void create() {
        //given
        Address address = Address.builder().city("서울시").district("강동구").street("미아로").build();
        Profile profile = Profile.builder().storeFileName("ASDAS-asDASDAS-aSDSA.jpg").uploadFileName("프로필 이미지").build();

        //when
        Member member = Member.builder().email("test@gmail.com").password("DF#Q$FWAD").address(address).nickName("테스트2").profile(profile).build();

        //then
        assertThat(member.getEmail()).isEqualTo("test@gmail.com");
        assertThat(member.getPassword()).isEqualTo("DF#Q$FWAD");
        assertThat(member.getNickName()).isEqualTo("테스트2");

        assertThat(member.getAddress()).isEqualTo(address);
        assertThat(member.getProfile()).isEqualTo(profile);
    }
    
    @Test
    @DisplayName("member 수정 - address")
    public void updateAddress() {
        //given
        Address address = Address.builder().city("서울시").district("강동구").street("미아로").build();
        Profile profile = Profile.builder().storeFileName("ASDAS-asDASDAS-aSDSA.jpg").uploadFileName("프로필 이미지").build();
        Member member = Member.builder().email("test@gmail.com").password("DF#Q$FWAD").address(address).nickName("테스트2").profile(profile).build();
        
        //when
        Address updateAddress = Address.builder().city("서울시").district("강남구").street("강남로").build();
        member.update(member.getProfile(), member.getNickName(), updateAddress);
        
        //then
        assertThat(member.getAddress().getCity()).isEqualTo(updateAddress.getCity());
        assertThat(member.getAddress().getDistrict()).isEqualTo(updateAddress.getDistrict());
        assertThat(member.getAddress().getStreet()).isEqualTo(updateAddress.getStreet());
    }

    @Test
    @DisplayName("member 수정 - nickName")
    public void updateNickName() {
        //given
        Address address = Address.builder().city("서울시").district("강동구").street("미아로").build();
        Profile profile = Profile.builder().storeFileName("ASDAS-asDASDAS-aSDSA.jpg").uploadFileName("프로필 이미지").build();
        Member member = Member.builder().email("test@gmail.com").password("DF#Q$FWAD").address(address).nickName("테스트2").profile(profile).build();

        //when
        member.update(member.getProfile(), "와바라바덥덥", member.getAddress());

        //then
        assertThat(member.getNickName()).isEqualTo("와바라바덥덥");
    }

    @Test
    @DisplayName("member 수정 - profile")
    public void updateProfile() {
        //given
        Address address = Address.builder().city("서울시").district("강동구").street("미아로").build();
        Profile profile = Profile.builder().storeFileName("ASDAS-asDASDAS-aSDSA").uploadFileName("프로필 이미지").build();
        Member member = Member.builder().email("test@gmail.com").password("DF#Q$FWAD").address(address).nickName("테스트2").profile(profile).build();

        //when
        Profile updateProfile = Profile.builder().storeFileName("SADASF-asDASDSA-ADSADSA").uploadFileName("업데이트 이미지").build();
        member.update(updateProfile, member.getNickName(), member.getAddress());

        //then
        assertThat(member.getProfile().getStoreFileName()).isEqualTo(updateProfile.getStoreFileName());
        assertThat(member.getProfile().getUploadFileName()).isEqualTo(updateProfile.getUploadFileName());
    }
}