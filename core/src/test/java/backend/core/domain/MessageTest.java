package backend.core.domain;

import backend.core.global.domain.Address;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageTest {

    @Test
    @DisplayName("Message 생성")
    public void createMessage() {
        //given
        Address address = Address.builder().city("서울시").district("테스트").street("성동로").build();
        Basket basket = Basket.builder().build();
        Member author = Member.builder().email("haha@gmail.com").password("DF#Q$FWAD").address(address).basket(basket).nickName("와바라바덥덥").build();
        Post post = Post.builder().member(author).description("테스트 설명글").title("테스트 제목").maximum(3).build();

        Chat chat = Chat.builder().post(post).build();
        Basket basket1 = Basket.builder().build();
        Member member = Member.builder().email("asdas@gmail.com").password("DF#asdsaw12").address(address).basket(basket1).nickName("띠용이").build();

        //when
        Message message = Message.builder().chat(chat).member(member).content("테스트 대화 내용 하하").build();

        //then
        assertThat(message.getContent()).isEqualTo("테스트 대화 내용 하하");
        assertThat(message.getChat().getMessageList().size()).isEqualTo(1);
        assertThat(message.getMember().getId()).isEqualTo(member.getId());
    }
}