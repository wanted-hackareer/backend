package backend.core.service;

import backend.core.domain.Basket;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class BasketServiceTest {

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
}