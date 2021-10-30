package backend.core.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String email;
    private String picture;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "member")
    private List<Staff> staffList = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "basket_id")
    private Basket basket;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rating_id")
    private Rating rating;

    //== 연관관계 메서드 ==//
    public void setBasket(Basket basket) {
        this.basket = basket;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    //== 비즈니스 로직 ==//
    @Builder
    public Member (String email, String picture, Address address, Basket basket, Rating rating) {
        this.email = email;
        this.picture = picture;
        this.address = address;
        this.role = Role.USER;
        setBasket(basket);
        setRating(rating);
    }
}
