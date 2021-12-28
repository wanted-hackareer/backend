package backend.core.item.domain;

import backend.core.basket.domain.Basket;
import backend.core.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "basket_id")
    private Basket basket;

    //== 연관관계 메서드 ==//
    public void setBasket(Basket basket) {
        if (this.basket != null) {
            this.basket.getItemList().remove(this);
        }
        this.basket = basket;
        basket.getItemList().add(this);
    }

    //== 비즈니스 로직 ==//
    @Builder
    public Item(String name, Basket basket, int quantity) {
        this.name = name;
        this.quantity = quantity;
        setBasket(basket);
    }

    public void update(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public void delete() {
        this.basket.getItemList().remove(this);
    }
}
