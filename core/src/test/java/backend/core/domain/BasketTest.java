package backend.core.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class BasketTest {

    @Test
    @DisplayName("Basket 생성하기")
    public void createBasket() {
        //given
        Address address = Address.builder().city("서울시").district("성동구").street("성동로").build();
        Member member = Member.builder().email("test@gmail.com").password("DF#Q$FWAD").address(address).nickName("와바라바덥덥").build();

        Post post = Post.builder().member(member).description("설명글입니다.").title("성동구 같이 장보러갈 사람").maximum(3).build();

        //when
        Basket basket = Basket.builder().post(post).build();

        //then
        assertThat(basket.getPost().getId()).isEqualTo(post.getId());
        assertThat(basket.getPost().getBasket()).isEqualTo(basket);
    }
}