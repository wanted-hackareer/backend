package backend.core.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Basket extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "basket")
    private Member member;

    @OneToMany(mappedBy = "basket")
    private List<Item> itemList = new ArrayList<>();

    //== 비즈니스 로직 ==//
    @Builder
    public Basket(Member member) {
        this.member = member;
    }
}
