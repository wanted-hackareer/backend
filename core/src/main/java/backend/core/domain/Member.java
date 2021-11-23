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
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String email;
    private String password;
    private String nickName;

    @Embedded
    private Address address;

    @Embedded
    private Profile profile;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "basket_id")
    private Basket basket;

    @OneToMany(mappedBy = "member")
    private List<Staff> staffList = new ArrayList<>();

    //== 연관관계 메서드 ==//
    public void setBasket(Basket basket) {
        this.basket = basket;
    }

    //== 비즈니스 로직 ==//
    @Builder
    public Member(String email, String password, String nickName, Profile profile, Address address) {
        this.email = email;
        this.password = password;
        this.profile = profile;
        this.nickName = nickName;
        this.address = address;
        this.role = Role.USER;
    }

    public void update(Profile profile, String nickName, Address address) {
        this.profile = profile;
        this.nickName = nickName;
        this.address = address;
    }

    public void updatePassword(String password) {
        this.password = password;
    }
}
