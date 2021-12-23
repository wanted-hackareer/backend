package backend.core.service;

import backend.core.domain.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static backend.core.dto.request.StaffRequestDto.StaffCreateRequestDto;
import static backend.core.dto.request.StaffRequestDto.StaffUpdateRequestDto;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class StaffServiceTest {

    @Autowired
    private StaffService staffService;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("staff 생성")
    public void create() {
        //given
        Address address = Address.builder().city("서울시").district("강동구").street("미아로").build();
        Profile profile = Profile.builder().storeFileName("ASDAS-asDASDAS-aSDSA.jpg").uploadFileName("프로필 이미지").build();
        Basket basket1 = Basket.builder().build();
        Basket basket2 = Basket.builder().build();
        Member member1 = Member.builder().email("test1@gmail.com").basket(basket1).password("DF#Q$FWAD").address(address).nickName("테스트1").profile(profile).build();
        Member member2 = Member.builder().email("test3@gmail.com").basket(basket2).password("DF#Q$FWAD").address(address).nickName("테스트3").profile(profile).build();
        Post post = Post.builder().title("테스트 제목").description("테스트 본문").member(member1).dayOfTheWeek("월, 금").maximum(2).build();

        em.persist(member2);
        em.persist(post);

        //when
        StaffCreateRequestDto staffCreateRequestDto = new StaffCreateRequestDto(post.getId(), member2.getId());
        Long staffId = staffService.save(staffCreateRequestDto);

        //then
        assertThat(staffService.findByIdOrThrow(staffId).getMember()).isInstanceOf(Member.class);
        assertThat(staffService.findByIdOrThrow(staffId).getPost()).isInstanceOf(Post.class);
        assertThat(staffService.findByIdOrThrow(staffId).getMember().getEmail()).isEqualTo(member2.getEmail());
        assertThat(staffService.findByIdOrThrow(staffId).getPost().getTitle()).isEqualTo(post.getTitle());
        assertThat(staffService.findByIdOrThrow(staffId).getStaffStatus()).isEqualTo(StaffStatus.WAIT);
    }

    @Test
    @DisplayName("staff 조회 - findAll")
    public void findAll() {
        //given
        Address address = Address.builder().city("서울시").district("강동구").street("미아로").build();
        Profile profile = Profile.builder().storeFileName("ASDAS-asDASDAS-aSDSA.jpg").uploadFileName("프로필 이미지").build();
        Basket basketA = Basket.builder().build();
        Basket basketB = Basket.builder().build();
        Basket basketC = Basket.builder().build();
        Member memberA = Member.builder().email("test1@gmail.com").basket(basketA).password("DF#Q$FWAD").address(address).nickName("테스트1").profile(profile).build();
        Member memberB = Member.builder().email("test3@gmail.com").basket(basketB).password("DF#Q$FWAD").address(address).nickName("테스트3").profile(profile).build();
        Member memberC = Member.builder().email("test4@gmail.com").basket(basketC).password("DF#Q$FWAD").address(address).nickName("테스트4").profile(profile).build();
        Post post = Post.builder().title("테스트 제목").description("테스트 본문").member(memberA).dayOfTheWeek("월, 금").maximum(2).build();
        em.persist(memberB);
        em.persist(memberC);
        em.persist(post);

        //when
        Staff staffA = Staff.builder().post(post).member(memberB).build();
        Staff staffB = Staff.builder().post(post).member(memberC).build();
        staffA.update(StaffStatus.ACCESS);
        staffB.update(StaffStatus.ACCESS);

        em.persist(staffA);
        em.persist(staffB);

        //then
        assertThat(staffService.findAllOrThrow(0, 100).size()).isEqualTo(2);
    }

    @Test
    @DisplayName("staff 조회 - findByStatus")
    public void findByStatus() {
        //given
        Address address = Address.builder().city("서울시").district("강동구").street("미아로").build();
        Profile profile = Profile.builder().storeFileName("ASDAS-asDASDAS-aSDSA.jpg").uploadFileName("프로필 이미지").build();
        Basket basketA = Basket.builder().build();
        Basket basketB = Basket.builder().build();
        Basket basketC = Basket.builder().build();
        Member memberA = Member.builder().email("test1@gmail.com").basket(basketA).password("DF#Q$FWAD").address(address).nickName("테스트1").profile(profile).build();
        Member memberB = Member.builder().email("test3@gmail.com").basket(basketB).password("DF#Q$FWAD").address(address).nickName("테스트3").profile(profile).build();
        Member memberC = Member.builder().email("test4@gmail.com").basket(basketC).password("DF#Q$FWAD").address(address).nickName("테스트4").profile(profile).build();
        Post post = Post.builder().title("테스트 제목").description("테스트 본문").member(memberA).dayOfTheWeek("월, 금").maximum(2).build();
        em.persist(memberB);
        em.persist(memberC);
        em.persist(post);

        //when
        Staff staffA = Staff.builder().post(post).member(memberB).build();
        Staff staffB = Staff.builder().post(post).member(memberC).build();
        staffA.update(StaffStatus.ACCESS);

        em.persist(staffA);
        em.persist(staffB);

        //then
        assertThat(staffService.findByStatusOrThrow(StaffStatus.WAIT).size()).isEqualTo(1);
        assertThat(staffService.findByStatusOrThrow(StaffStatus.ACCESS).size()).isEqualTo(1);
    }

    @Test
    @DisplayName("staff 수정 - ACCEPT")
    public void updateACCEPT() {
        //given
        Address address = Address.builder().city("서울시").district("강동구").street("미아로").build();
        Profile profile = Profile.builder().storeFileName("ASDAS-asDASDAS-aSDSA.jpg").uploadFileName("프로필 이미지").build();
        Basket basket1 = Basket.builder().build();
        Basket basket2 = Basket.builder().build();
        Member member1 = Member.builder().email("test1@gmail.com").password("DF#Q$FWAD").basket(basket1).address(address).nickName("테스트1").profile(profile).build();
        Member member2 = Member.builder().email("test3@gmail.com").password("DF#Q$FWAD").basket(basket2).address(address).nickName("테스트3").profile(profile).build();
        Post post = Post.builder().title("테스트 제목").description("테스트 본문").member(member1).dayOfTheWeek("월, 금").maximum(2).build();
        Staff staff = Staff.builder().member(member2).post(post).build();
        em.persist(member2);
        em.persist(post);
        em.persist(staff);

        //when
        StaffUpdateRequestDto staffUpdateRequestDto = new StaffUpdateRequestDto(staff.getId(), StaffStatus.ACCESS);
        Long staffId = staffService.updateOrThrow(staffUpdateRequestDto);

        //then
        assertThat(staffService.findByIdOrThrow(staffId).getStaffStatus()).isEqualTo(StaffStatus.ACCESS);
    }

    @Test
    @DisplayName("staff 수정 - CANCEL")
    public void updateCANCEL() {
        //given
        Address address = Address.builder().city("서울시").district("강동구").street("미아로").build();
        Profile profile = Profile.builder().storeFileName("ASDAS-asDASDAS-aSDSA.jpg").uploadFileName("프로필 이미지").build();

        Basket basket1 = Basket.builder().build();
        Basket basket2 = Basket.builder().build();

        Member member1 = Member.builder().email("test1@gmail.com").password("DF#Q$FWAD").basket(basket1).address(address).nickName("테스트1").profile(profile).build();
        Member member2 = Member.builder().email("test3@gmail.com").password("DF#Q$FWAD").basket(basket2).address(address).nickName("테스트3").profile(profile).build();
        Post post = Post.builder().title("테스트 제목").description("테스트 본문").member(member1).dayOfTheWeek("월, 금").maximum(2).build();
        Staff staff = Staff.builder().member(member2).post(post).build();
        em.persist(member2);
        em.persist(post);
        em.persist(staff);

        //when
        StaffUpdateRequestDto staffUpdateRequestDto = new StaffUpdateRequestDto(staff.getId(), StaffStatus.CANCEL);
        Long staffId = staffService.updateOrThrow(staffUpdateRequestDto);

        //then
        assertThat(staffService.findByIdOrThrow(staffId).getStaffStatus()).isEqualTo(StaffStatus.CANCEL);
    }

    @Test
    @DisplayName("staff 수정 - DENIED")
    public void updateDENIED() {
        //given
        Address address = Address.builder().city("서울시").district("강동구").street("미아로").build();
        Profile profile = Profile.builder().storeFileName("ASDAS-asDASDAS-aSDSA.jpg").uploadFileName("프로필 이미지").build();
        Basket basket1 = Basket.builder().build();
        Basket basket2 = Basket.builder().build();

        Member member1 = Member.builder().email("test1@gmail.com").basket(basket1).password("DF#Q$FWAD").address(address).nickName("테스트1").profile(profile).build();
        Member member2 = Member.builder().email("test3@gmail.com").basket(basket2).password("DF#Q$FWAD").address(address).nickName("테스트3").profile(profile).build();
        Post post = Post.builder().title("테스트 제목").description("테스트 본문").member(member1).dayOfTheWeek("월, 금").maximum(2).build();
        Staff staff = Staff.builder().member(member2).post(post).build();

        em.persist(member2);
        em.persist(post);
        em.persist(staff);

        //when
        StaffUpdateRequestDto staffUpdateRequestDto = new StaffUpdateRequestDto(staff.getId(), StaffStatus.DENIED);
        Long staffId = staffService.updateOrThrow(staffUpdateRequestDto);

        //then
        assertThat(staffService.findByIdOrThrow(staffId).getStaffStatus()).isEqualTo(StaffStatus.DENIED);
    }
}