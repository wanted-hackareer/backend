package backend.core.service;

import backend.core.domain.Basket;
import backend.core.dto.request.ItemRequestDto;
import backend.core.global.error.exception.CustomException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static backend.core.dto.request.ItemRequestDto.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class BasketServiceTest {

    @Autowired
    private ItemService itemService;

    @Autowired
    private BasketService basketService;

    @Test
    @DisplayName("basket 생성")
    public void save() {
        //given
        Long id = basketService.save();

        //when
        Basket basket = basketService.findByIdOrThrow(id);

        //then
        assertThat(basket.getId()).isEqualTo(id);
        assertThat(basket).isInstanceOf(Basket.class);
    }

    @Test
    @DisplayName("basket 조회 - findAll")
    public void findAll() {
        //given
        Long idA = basketService.save();
        Long idB = basketService.save();

        //when

        //then
        assertThat(basketService.findAllOrThrow(0, 100).size()).isEqualTo(2);
    }

    @Test
    @DisplayName("basket 삭제 - delete")
    public void deleteBasket() {
        //given
        Long id = basketService.save();
        ItemCreateRequestDto dtoA = new ItemCreateRequestDto(id, "사과", 3);
        ItemCreateRequestDto dtoB = new ItemCreateRequestDto(id, "물 1.5L", 3);
        itemService.save(dtoA);
        itemService.save(dtoB);

        //when
        basketService.deleteById(id);

        //then
        Assertions.assertThrows(CustomException.class, () -> {
            basketService.findByIdOrThrow(id);
        });
    }
}