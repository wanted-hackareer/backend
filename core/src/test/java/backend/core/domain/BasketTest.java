package backend.core.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BasketTest {

    @Test
    @DisplayName("Basket 생성하기")
    public void createBasket() {
        //given
        Address address = Address.builder().city("서울시").district("강남구").street("강남로").build();
        Member member = Member.builder().address(address).email("test@gmail.com").picture("/qwer-qweqw-sdsa.png").build();

        //when
        Basket basket = Basket.builder().member(member).build();

        //then
        Assertions.assertThat(basket.getMember().getId()).isEqualTo(member.getId());
    }

}