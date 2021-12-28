package backend.core.basket.domain;

import backend.core.domain.BaseTimeEntity;
import backend.core.domain.Member;
import backend.core.item.domain.Item;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Basket extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "basket")
    private Member member;

    @OneToMany(mappedBy = "basket", cascade = CascadeType.REMOVE)
    private List<Item> itemList = new ArrayList<>();

    //== 연관관계 메서드 ==//
    public void setMember(Member member) {
        this.member = member;
    }

    //== 비즈니스 로직 ==//
    @Builder
    public Basket() {
    }
}
