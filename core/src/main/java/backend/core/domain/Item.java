package backend.core.domain;

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
    public Item (String name, Basket basket) {
        this.name = name;
        setBasket(basket);
    }
}
