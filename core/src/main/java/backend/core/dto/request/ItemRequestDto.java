package backend.core.dto.request;

import backend.core.domain.Basket;
import backend.core.domain.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ItemRequestDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ItemCreateRequestDto {
        private Long basketId;
        private String name;
        private int quantity;

        public ItemCreateRequestDto(Long basketId, String name, int quantity) {
            this.basketId = basketId;
            this.name = name;
            this.quantity = quantity;
        }

        public Item toEntity(Basket basket) {
            return Item.builder()
                    .quantity(quantity)
                    .name(name)
                    .basket(basket)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ItemUpdateRequestDto {
        private Long id;
        private String name;
        private int quantity;

        public ItemUpdateRequestDto(Long id, String name, int quantity) {
            this.id = id;
            this.name = name;
            this.quantity = quantity;
        }
    }
}
