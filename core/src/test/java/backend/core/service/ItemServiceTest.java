package backend.core.service;

import backend.core.domain.*;
import backend.core.global.error.exception.CustomException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static backend.core.dto.request.ItemRequestDto.ItemCreateRequestDto;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class ItemServiceTest {

    @Autowired
    private ItemService itemService;

    @Autowired
    private EntityManager em;

    private Basket basket;
    private Member member;

    @BeforeEach
    public void init() {
        Address address = Address.builder().city("부산광역시").district("강서구").street("아무로").build();
        Profile profile = Profile.builder().storeFileName("ASDAS-asDASDAS-dsada.jpg").uploadFileName("프로필 이미지").build();
        basket = Basket.builder().build();
        member = Member.builder().profile(profile).address(address).nickName("삐약삐약").password("sdkjfa-caiasnc=ascsabkck").basket(basket).email("god@gmail.com").build();
        em.persist(member);
        em.persist(basket);
    }

    @Test
    @DisplayName("item 생성")
    public void create() {
        //given
        ItemCreateRequestDto dto = new ItemCreateRequestDto(basket.getId(), "사과", 3);

        //when
        Long itemId = itemService.save(dto);

        //then
        assertThat(itemService.findByIdOrThrow(itemId)).isInstanceOf(Item.class);
        assertThat(itemService.findByIdOrThrow(itemId).getName()).isEqualTo("사과");
        assertThat(itemService.findByIdOrThrow(itemId).getQuantity()).isEqualTo(3);
        assertThat(itemService.findByIdOrThrow(itemId).getBasket().getId()).isEqualTo(basket.getId());
    }

    @Test
    @DisplayName("Item 조회 - findAll")
    public void findAll() {
        //given
        Item itemA = Item.builder().basket(basket).name("바나나").quantity(3).build();
        Item itemB = Item.builder().basket(basket).name("휴지").quantity(5).build();

        //when
        em.persist(itemA);
        em.persist(itemB);

        //then
        assertThat(itemService.findAllOrThrow(0, 100).size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Item 삭제 - delete")
    public void delete() {
        //given
        Item item = Item.builder().basket(basket).name("바나나 트리").quantity(3).build();
        em.persist(item);

        //when
        Long id = itemService.delete(item.getId());

        //then
        Assertions.assertThrows(CustomException.class, () -> {
            itemService.delete(id);
        });
    }
}