package backend.core.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class TagTest {

    @Test
    @DisplayName("Tag 생성")
    public void createTag() {
        //given
        Address address = Address.builder().city("서울시").district("테스트").street("성동로").build();
        Basket basket = Basket.builder().build();
        Member member = Member.builder().email("test@gmail.com").password("DF#Q$FWAD").address(address).basket(basket).nickName("와바라바덥덥").build();
        Post post = Post.builder().member(member).description("테스트 설명글").title("테스트 제목").maximum(3).build();

        //when
        Tag tag = Tag.builder().name("이마트 트레이더스").post(post).build();

        //then
        assertThat(tag.getName()).isEqualTo("이마트 트레이더스");
        assertThat(tag.getPost().getTagList().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Tag 수정 - name")
    public void updateName() {
        //given
        Address address = Address.builder().city("서울시").district("테스트").street("성동로").build();
        Basket basket = Basket.builder().build();
        Member member = Member.builder().email("test@gmail.com").password("DF#Q$FWAD").address(address).basket(basket).nickName("와바라바덥덥").build();
        Post post = Post.builder().member(member).description("테스트 설명글").title("테스트 제목").maximum(3).build();
        Tag tag = Tag.builder().name("이마트 트레이더스").post(post).build();

        //when
        tag.update("홈플러스 익스프레스");

        //then
        assertThat(tag.getName()).isEqualTo("홈플러스 익스프레스");
        assertThat(tag.getPost().getTagList().size()).isEqualTo(1);
    }
}