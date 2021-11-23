package backend.core.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ChatTest {

    @Test
    @DisplayName("Chat 생성")
    public void createChat() {
        //given
        Address address = Address.builder().city("서울시").district("테스트").street("성동로").build();
        Basket basket = Basket.builder().build();
        Member author = Member.builder().email("haha@gmail.com").password("DF#Q$FWAD").address(address).basket(basket).nickName("와바라바덥덥").build();
        Post post = Post.builder().member(author).description("테스트 설명글").title("테스트 제목").maximum(3).build();

        //when
        Chat chat = Chat.builder().post(post).build();

        //then
        assertThat(chat.getPost()).isEqualTo(post);
    }
}