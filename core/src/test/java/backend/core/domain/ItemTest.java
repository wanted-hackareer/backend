package backend.core.domain;

import backend.core.global.domain.Address;
import backend.core.item.domain.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ItemTest {

    @Test
    @DisplayName("Item 생성")
    public void createItem() {
        //given
        Address address = Address.builder().city("서울시").district("성동구").street("성동로").build();
        Basket basket = Basket.builder().build();
        Member member = Member.builder().email("test@gmail.com").password("DF#Q$FWAD").address(address).basket(basket).nickName("와바라바덥덥").build();

        //when
        Item item = Item.builder().name("사과").basket(basket).build();

        //then
        assertThat(item.getBasket()).isEqualTo(basket);
        assertThat(item.getName()).isEqualTo("사과");
        assertThat(item.getBasket().getItemList().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Item 수정 - name")
    public void updateName() {
        //given
        Address address = Address.builder().city("서울시").district("강남구").street("성동로").build();
        Basket basket = Basket.builder().build();
        Member member = Member.builder().email("test2@gmail.com").password("DF#Q$FWAD").address(address).basket(basket).nickName("와바라바덥덥").build();

        Item item = Item.builder().name("사과").basket(basket).quantity(1).build();

        //when
        item.update("풋사과", item.getQuantity());

        //then
        assertThat(item.getName()).isEqualTo("풋사과");
        assertThat(item.getBasket().getItemList().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Item 수정 - quantity")
    public void updateQuantity() {
        //given
        Address address = Address.builder().city("서울시").district("강남구").street("성동로").build();
        Basket basket = Basket.builder().build();
        Member member = Member.builder().email("test2@gmail.com").password("DF#Q$FWAD").address(address).basket(basket).nickName("와바라바덥덥").build();

        Item item = Item.builder().name("사과").basket(basket).quantity(1).build();

        //when
        item.update(item.getName(), 2);

        //then
        assertThat(item.getQuantity()).isEqualTo(2);
        assertThat(item.getBasket().getItemList().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Item 삭제")
    public void deleteItem() {
        //given
        Address address = Address.builder().city("서울시").district("강남구").street("성동로").build();
        Basket basket = Basket.builder().build();
        Member member = Member.builder().email("test2@gmail.com").password("DF#Q$FWAD").address(address).basket(basket).nickName("와바라바덥덥").build();

        Item item = Item.builder().name("사과").basket(basket).quantity(1).build();

        //when
        item.delete();

        //then
        assertThat(item.getBasket().getItemList().isEmpty()).isTrue();
    }
}