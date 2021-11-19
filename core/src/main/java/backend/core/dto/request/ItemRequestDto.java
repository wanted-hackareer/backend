package backend.core.dto.request;

import backend.core.domain.Basket;
import backend.core.domain.Item;
import lombok.Getter;

public class ItemRequestDto {

    @Getter
    public static class ItemCreateRequestDto {
        private Long basketId;
        private String name;
        private int quantity;

        private Basket basket;

        public ItemCreateRequestDto(Long basketId, String name, int quantity) {
            this.basketId = basketId;
            this.name = name;
            this.quantity = quantity;
        }

        public Item toEntity() {
            return Item.builder()
                    .quantity(quantity)
                    .name(name)
                    .basket(basket)
                    .build();
        }

        public void setBasket(Basket basket) {
            this.basket = basket;
        }
    }
}
