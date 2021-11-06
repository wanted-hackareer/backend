package backend.core.domain;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StaffTest {

    @Test
    @DisplayName("Staff 생성")
    public void createStaff() {
        //given
        Address address = Address.builder().city("서울시").district("성동구").street("성동로").build();
        Member member = Member.builder().email("test@gmail.com").password("DF#Q$FWAD").address(address).nickName("와바라바덥덥").build();

        Post post = Post.builder().member(member).description("설명글입니다.").title("성동구 같이 장보러갈 사람").maximum(3).build();

        //when
        Staff staff = Staff.builder().member(member).post(post).build();

        //then
        assertThat(staff.getMember().getEmail()).isEqualTo(member.getEmail());
        assertThat(staff.getPost().getTitle()).isEqualTo(post.getTitle());
        assertThat(staff.getPost().getStaffList().size()).isEqualTo(1);
        assertThat(staff.getStaffStatus()).isEqualTo(StaffStatus.WAIT);
    }

    @Test
    @DisplayName("Staff 수정 - Access")
    public void updateStaffAccess() {
        //given
        Address address = Address.builder().city("서울시").district("성동구").street("성동로").build();
        Member member = Member.builder().email("test@gmail.com").password("DF#Q$FWAD").address(address).nickName("와바라바덥덥").build();

        Post post = Post.builder().member(member).description("설명글입니다.").title("성동구 같이 장보러갈 사람").maximum(3).build();
        Staff staff = Staff.builder().member(member).post(post).build();

        //when
        staff.update(StaffStatus.ACCESS);

        //then
        assertThat(staff.getStaffStatus()).isEqualTo(StaffStatus.ACCESS);
        assertThat(staff.getPost().getStaffList().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Staff 수정 - Cancel")
    public void updateStatusCancel() {
        //given
        Address address = Address.builder().city("서울시").district("성동구").street("성동로").build();
        Member member = Member.builder().email("test@gmail.com").password("DF#Q$FWAD").address(address).nickName("와바라바덥덥").build();

        Post post = Post.builder().member(member).description("설명글입니다.").title("성동구 같이 장보러갈 사람").maximum(3).build();
        Staff staff = Staff.builder().member(member).post(post).build();

        //when
        staff.update(StaffStatus.CANCEL);

        //then
        assertThat(staff.getStaffStatus()).isEqualTo(StaffStatus.CANCEL);
        assertThat(staff.getPost().getStaffList().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Staff 수정 - Denied")
    public void updateStatusDenied() {
        //given
        Address address = Address.builder().city("서울시").district("성동구").street("성동로").build();
        Member member = Member.builder().email("test@gmail.com").password("DF#Q$FWAD").address(address).nickName("와바라바덥덥").build();

        Post post = Post.builder().member(member).description("설명글입니다.").title("성동구 같이 장보러갈 사람").maximum(3).build();
        Staff staff = Staff.builder().member(member).post(post).build();

        //when
        staff.update(StaffStatus.DENIED);

        //then
        assertThat(staff.getStaffStatus()).isEqualTo(StaffStatus.DENIED);
        assertThat(staff.getPost().getStaffList().size()).isEqualTo(1);
    }
}