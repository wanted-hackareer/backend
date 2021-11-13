package backend.core.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BasketTest {

    @Test
    @DisplayName("Basket 생성하기")
    public void createBasket() {
        //given
        Address address = Address.builder().city("서울시").district("성동구").street("성동로").build();
        Member member = Member.builder()
                .email("test@gmail.com").password("DF#Q$FWAD").address(address).nickName("와바라바덥덥").build();

        //when
        Basket basket = Basket.builder().member(member).build();

        //then
        assertThat(basket.getMember().getId()).isEqualTo(member.getId());
        assertThat(basket.getMember().getBasket()).isEqualTo(basket);
    }
}