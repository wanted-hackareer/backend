package backend.core.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CommentTest {

    @Test
    @DisplayName("Comment 생성")
    public void createComment() {
        //given
        Address address = Address.builder().city("서울시").district("테스트").street("성동로").build();
        Basket basket = Basket.builder().build();
        Member author = Member.builder().email("test@gmail.com").password("DF#Q$FWAD").address(address).basket(basket).nickName("와바라바덥덥").build();
        Post post = Post.builder().member(author).description("테스트 설명글").title("테스트 제목").maximum(3).build();

        Basket basketA = Basket.builder().build();
        Member member = Member.builder().email("turieowp@gmail.com").basket(basketA).password("asdadfafgadfWAD").address(address).nickName("띠용이").build();

        //when
        Comment comment = Comment.builder().member(member).post(post).content("혹시 휴지 사고 싶은데 휴지도 사나요??").build();

        //then
        assertThat(comment.getContent()).isEqualTo("혹시 휴지 사고 싶은데 휴지도 사나요??");
        assertThat(comment.getMember().getEmail()).isEqualTo("turieowp@gmail.com");
        assertThat(comment.getPost().getCommentList().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Comment 수정 - Content")
    public void updateContent() {
        //given
        Address address = Address.builder().city("서울시").district("테스트").street("성동로").build();
        Basket basket = Basket.builder().build();
        Member author = Member.builder().email("haha@gmail.com").password("DF#Q$FWAD").address(address).basket(basket).nickName("와바라바덥덥").build();
        Post post = Post.builder().member(author).description("테스트 설명글").title("테스트 제목").maximum(3).build();

        Basket basketA = Basket.builder().build();
        Member member = Member.builder().email("turieowp@gmail.com").basket(basketA).password("asdadfafgadfWAD").address(address).nickName("쀼슝이").build();
        Comment comment = Comment.builder().member(member).post(post).content("혹시 휴지 사고 싶은데 휴지도 사나요??").build();

        //when
        comment.update("bbq 황금 올리브 먹고 싶다.");

        //then
        assertThat(comment.getContent()).isEqualTo("bbq 황금 올리브 먹고 싶다.");
        assertThat(comment.getPost().getCommentList().size()).isEqualTo(1);
    }
}
