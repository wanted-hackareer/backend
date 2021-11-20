package backend.core.service;

import backend.core.domain.*;
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

    @Test
    @DisplayName("item 생성")
    public void create() {
        //given
        Address address = Address.builder().city("부산광역시").district("강서구").street("아무로").build();
        Profile profile = Profile.builder().storeFileName("ASDAS-asDASDAS-dsada.jpg").uploadFileName("프로필 이미지").build();
        Member member = Member.builder().profile(profile).address(address).nickName("삐약삐약").password("sdkjfa-caiasnc=ascsabkck").email("god@gmail.com").build();
        Basket basket = Basket.builder().member(member).build();
        em.persist(member);
        em.persist(basket);

        //when
        ItemCreateRequestDto dto = new ItemCreateRequestDto(basket.getId(), "사과", 3);
        dto.setBasket(basket);
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
        Address address = Address.builder().city("부산광역시").district("강서구").street("아무로").build();
        Profile profile = Profile.builder().storeFileName("ASDAS-asDASDAS-dsada.jpg").uploadFileName("프로필 이미지").build();
        Member member = Member.builder().profile(profile).address(address).nickName("삐약삐약").password("sdkjfa-caiasnc=ascsabkck").email("god@gmail.com").build();
        Basket basket = Basket.builder().member(member).build();
        em.persist(member);
        em.persist(basket);

        //when
        Item itemA = Item.builder().basket(basket).name("바나나").quantity(3).build();
        Item itemB = Item.builder().basket(basket).name("휴지").quantity(5).build();
        em.persist(itemA);
        em.persist(itemB);

        //then
        assertThat(itemService.findAllOrThrow(0, 100).size()).isEqualTo(2);
    }
}