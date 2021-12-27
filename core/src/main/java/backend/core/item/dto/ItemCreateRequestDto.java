package backend.core.item.dto;

import backend.core.domain.Basket;
import backend.core.item.domain.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemCreateRequestDto {
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
