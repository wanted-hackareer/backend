package backend.core.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PostTest {

    @Test
    @DisplayName("Post 생성")
    public void createPost() {
        //given
        Address address = Address.builder().city("서울시").district("테스트").street("성동로").build();
        Member member = Member.builder().email("test@gmail.com").password("DF#Q$FWAD").address(address).nickName("와바라바덥덥").build();

        //when
        Post post = Post.builder().member(member).description("테스트 설명글").title("테스트 제목").dayOfTheWeek("월, 화, 수").maximum(3).build();

        //then
        assertThat(post.getTitle()).isEqualTo("테스트 제목");
        assertThat(post.getAddress()).isEqualTo(address);
        assertThat(post.getDayOfTheWeek()).isEqualTo("월, 화, 수");
        assertThat(post.getDescription()).isEqualTo("테스트 설명글");
        assertThat(post.getMember()).isEqualTo(member);
        assertThat(post.getMaximum()).isEqualTo(3);
    }

    @Test
    @DisplayName("Post 수정 - Description")
    public void updateDescription() {
        //given
        Address address = Address.builder().city("서울시").district("테스트").street("성동로").build();
        Member member = Member.builder().email("test@gmail.com").password("DF#Q$FWAD").address(address).nickName("와바라바덥덥").build();

        Post post = Post.builder().member(member).description("테스트 설명글").title("테스트 제목").dayOfTheWeek("월, 화").maximum(3).build();

        //when
        post.update(post.getTitle(), "수정 후 설명글", post.getMaximum(), post.getPostStatus(), post.getDayOfTheWeek());

        //then
        assertThat(post.getTitle()).isEqualTo(post.getTitle());
        assertThat(post.getMaximum()).isEqualTo(post.getMaximum());
        assertThat(post.getPostStatus()).isEqualTo(post.getPostStatus());
        assertThat(post.getDescription()).isEqualTo("수정 후 설명글");
        assertThat(post.getDayOfTheWeek()).isEqualTo(post.getDayOfTheWeek());
    }

    @Test
    @DisplayName("Post 수정 - Title")
    public void updateTitle() {
        //given
        Address address = Address.builder().city("서울시").district("테스트").street("성동로").build();
        Member member = Member.builder().email("test@gmail.com").password("DF#Q$FWAD").address(address).nickName("와바라바덥덥").build();

        Post post = Post.builder().member(member).description("테스트 설명글").title("테스트 제목").dayOfTheWeek("월, 화").maximum(3).build();

        //when
        post.update("수정 후 제목", post.getDescription(), post.getMaximum(), post.getPostStatus(), post.getDayOfTheWeek());

        //then
        assertThat(post.getDescription()).isEqualTo(post.getDescription());
        assertThat(post.getMaximum()).isEqualTo(post.getMaximum());
        assertThat(post.getPostStatus()).isEqualTo(post.getPostStatus());
        assertThat(post.getTitle()).isEqualTo("수정 후 제목");
        assertThat(post.getDayOfTheWeek()).isEqualTo(post.getDayOfTheWeek());
    }

    @Test
    @DisplayName("Post 수정 - PostStatus")
    public void updateStatus() {
        //given
        Address address = Address.builder().city("서울시").district("테스트").street("성동로").build();
        Member member = Member.builder().email("test@gmail.com").password("DF#Q$FWAD").address(address).nickName("와바라바덥덥").build();

        Post post = Post.builder().member(member).description("테스트 설명글").title("테스트 제목").dayOfTheWeek("월, 화").maximum(3).build();

        //when
        post.update(post.getTitle(), post.getDescription(), post.getMaximum(), PostStatus.DONE, post.getDayOfTheWeek());

        //then
        assertThat(post.getTitle()).isEqualTo(post.getTitle());
        assertThat(post.getMaximum()).isEqualTo(post.getMaximum());
        assertThat(post.getDescription()).isEqualTo(post.getDescription());
        assertThat(post.getPostStatus()).isEqualTo(PostStatus.DONE);
        assertThat(post.getDayOfTheWeek()).isEqualTo(post.getDayOfTheWeek());
    }

    @Test
    @DisplayName("Post 수정 - Maximum")
    public void updateMaximum() {
        //given
        Address address = Address.builder().city("서울시").district("테스트").street("성동로").build();
        Member member = Member.builder().email("test@gmail.com").password("DF#Q$FWAD").address(address).nickName("와바라바덥덥").build();

        Post post = Post.builder().member(member).description("테스트 설명글").title("테스트 제목").dayOfTheWeek("월, 화").maximum(3).build();

        //when
        post.update(post.getTitle(), post.getDescription(), 2, post.getPostStatus(), post.getDayOfTheWeek());

        //then
        assertThat(post.getTitle()).isEqualTo(post.getTitle());
        assertThat(post.getMaximum()).isEqualTo(2);
        assertThat(post.getDescription()).isEqualTo(post.getDescription());
        assertThat(post.getPostStatus()).isEqualTo(post.getPostStatus());
        assertThat(post.getDayOfTheWeek()).isEqualTo(post.getDayOfTheWeek());
    }

    @Test
    @DisplayName("Post 수정 - DayOfTheWeek")
    public void updateDayOfTheWeek() {
        //given
        Address address = Address.builder().city("서울시").district("테스트").street("성동로").build();
        Member member = Member.builder().email("test@gmail.com").password("DF#Q$FWAD").address(address).nickName("와바라바덥덥").build();

        Post post = Post.builder().member(member).description("테스트 설명글").title("테스트 제목").dayOfTheWeek("월, 화").maximum(3).build();

        //when
        post.update(post.getTitle(), post.getDescription(), post.getMaximum(), post.getPostStatus(), "월, 화, 수, 목");

        //then
        assertThat(post.getTitle()).isEqualTo(post.getTitle());
        assertThat(post.getMaximum()).isEqualTo(post.getMaximum());
        assertThat(post.getDescription()).isEqualTo(post.getDescription());
        assertThat(post.getPostStatus()).isEqualTo(post.getPostStatus());
        assertThat(post.getDayOfTheWeek()).isEqualTo("월, 화, 수, 목");
    }
}